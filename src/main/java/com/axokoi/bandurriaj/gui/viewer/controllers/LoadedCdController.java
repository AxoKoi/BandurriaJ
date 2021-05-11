package com.axokoi.bandurriaj.gui.viewer.controllers;

import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.commons.popups.AlreadyLoadedCdPopupView;
import com.axokoi.bandurriaj.gui.commons.popups.TaggingDiscPopupView;
import com.axokoi.bandurriaj.gui.viewer.views.LoadedCdView;
import com.axokoi.bandurriaj.model.*;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import com.axokoi.bandurriaj.services.dataaccess.ExternalIdentifierService;
import com.axokoi.bandurriaj.services.dataaccess.UserConfigurationService;
import com.axokoi.bandurriaj.services.tagging.TaggingFacade;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Slf4j
@Component
public class LoadedCdController {
   @Autowired
   private DiscService discService;
   @Autowired
   private CatalogueRepository catalogueRepository;
   @Autowired
   private ArtistService artistService;
   @Autowired
   private DiscController discController;
   @Autowired
   private CatalogueController catalogueController;
   @Autowired
   private LoadedCdView loadedCdView;
   @Autowired
   private TaggingFacade taggingFacade;

   private final TaggingDiscPopupView taggingDiscPopupView;
   private final ThreadPoolTaskExecutor executorService;
   private final UserConfigurationService userConfigurationService;
   private final PopUpDisplayer popUpDisplayer;
   private final AlreadyLoadedCdPopupView alreadyLoadedCdView;
   private final ExternalIdentifierService externalIdentifierService;

   public LoadedCdController(TaggingDiscPopupView taggingDiscPopupView, ThreadPoolTaskExecutor executorService, UserConfigurationService userConfigurationService, PopUpDisplayer popUpDisplayer, AlreadyLoadedCdPopupView alreadyLoadedCdView, ExternalIdentifierService externalIdentifierService) {
      this.taggingDiscPopupView = taggingDiscPopupView;
      this.executorService = executorService;

      this.userConfigurationService = userConfigurationService;
      this.popUpDisplayer = popUpDisplayer;
      this.alreadyLoadedCdView = alreadyLoadedCdView;
      this.externalIdentifierService = externalIdentifierService;
   }

   public Disc saveCdOnCatalogue(Disc disc, Catalogue catalogue) {
      //For the moment we only have MusicBrainz implemented so we just pick the only one in the set
      Optional<ExternalIdentifier> externalIdentifier = disc.getExternalIdentifiers().stream().filter(Objects::nonNull)
              .filter(x -> x.getType() == ExternalIdentifier.Type.MUSICBRAINZ)
              .findAny();

      Optional<Disc> discByExternalIdentifier = Optional.empty();
      if (externalIdentifier.isPresent()) {
         discByExternalIdentifier = discService.findByExternalIdentifier(externalIdentifier.get());
      }

      //Return fast if the cd is already present on one of the catalogues.
      if (discByExternalIdentifier.isPresent()) {
         popUpDisplayer.displayNewPopupWithFunction(alreadyLoadedCdView, null, () -> null);
         return discByExternalIdentifier.get();
      }


      //Complete general the discinfo
      var userExternalIdentifier = new ExternalIdentifier();
      userExternalIdentifier.setType(ExternalIdentifier.Type.USER);
      userExternalIdentifier.setIdentifier(externalIdentifierService.getNextUserIdentifier());

      Set<Artist> creditedArtistsToPersist = new HashSet<>();
      Set<Artist> relatedArtistToPersists = new HashSet<>();

      //If we can use a tagger provider
      if(externalIdentifier.isPresent()) {
         // Create the task for looking the metadata in the background
         Future<?> futureTaggedDisc = retrieveDiscCorrespondingToIdentifier(externalIdentifier.get());

         popUpDisplayer.displayNewPopupWithFunction(taggingDiscPopupView, null, () -> null);
         disc = getDiscFromFuture(externalIdentifier.get(), futureTaggedDisc);
         creditedArtistsToPersist = getArtistsToPersists(disc.getCreditedArtists());
         relatedArtistToPersists = getArtistsToPersists(disc.getRelatedArtist());
         disc.setCreditedArtists(creditedArtistsToPersist);
         disc.setRelatedArtist(relatedArtistToPersists);
      }
      disc.addExternalIdentifier(userExternalIdentifier);
      catalogue.getDiscs().add(disc);
      persistsCdOnCatalogue(catalogue, creditedArtistsToPersist, relatedArtistToPersists, disc);
      userConfigurationService.saveConfiguration(UserConfiguration.Keys.LAST_CATALOGUE_USED, catalogue.getId().toString());

      return disc;
   }

   private Disc getDiscFromFuture(ExternalIdentifier externalIdentifier, Future<?> futureTaggedDisc) {
      Disc disc;
      try {
         disc = (Disc) futureTaggedDisc.get();
      } catch (InterruptedException | ExecutionException e) {
         Thread.currentThread().interrupt();
         throw new RuntimeException("Impossible to tag cd with external identifier:" + externalIdentifier.getType() + ":" +
                 externalIdentifier.getIdentifier());
      }
      return disc;
   }

   private Future<?> retrieveDiscCorrespondingToIdentifier(ExternalIdentifier externalIdentifier) {
      return executorService.submit(() -> {
         Disc loadedCd = taggingFacade.getDiscFromUniqueIdentifier(externalIdentifier).orElseThrow(() -> new RuntimeException("Impossible to tag cd with external identifier:" + externalIdentifier.getType() + ":" +
                 externalIdentifier.getIdentifier()));
         log.info("Cd tagged was: {}", externalIdentifier.getIdentifier());
         Platform.runLater(() -> ((Stage) taggingDiscPopupView.getScene().getWindow()).close());
         return loadedCd;
      });
   }

   private Set<Artist> getArtistsToPersists(Set<Artist> creditedArtist) {
      creditedArtist.forEach(x -> Assert.notNull(x.getMbIdentifier(), "MBIdentifier can't be null"));
      return creditedArtist.stream().map(x -> artistService.findByMbIdentifier(x.getMbIdentifier()).orElse(x)).collect(Collectors.toSet());
   }

   @Transactional
   protected void persistsCdOnCatalogue(Catalogue catalogue, Set<Artist> artistsToPersist, Set<Artist> relatedArtistToPersists, Disc discToPersist) {
      artistsToPersist.forEach(artistService::save);
      relatedArtistToPersists.forEach(artistService::save);
      discService.save(discToPersist);
//IRO ERror when saving
      catalogueRepository.save(catalogue);
   }

   public void refreshView(List<Disc> loadedCds) {
      loadedCdView.refresh(loadedCds);
   }

   public List<Catalogue> getCatalogues() {
      return IterableUtils.toList(catalogueRepository.findAll());
   }

   public void displayCd(Disc discToDisplay) {
      discController.displayViewCenter(discToDisplay);
   }

   public void dispatchRefreshToCatalogue() {
      catalogueController.refreshView();
   }

   public Optional<Catalogue> getLastUsedCatalogue() {
      Optional<String> catalogueId = userConfigurationService.findValueByKey(UserConfiguration.Keys.LAST_CATALOGUE_USED);
      return catalogueId.flatMap(s -> catalogueRepository.findById(Long.parseLong(s)));
   }
}
