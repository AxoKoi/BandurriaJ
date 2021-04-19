package com.axokoi.bandurriaj.gui.viewer.controllers;

import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.commons.popups.TaggingDiscPopupView;
import com.axokoi.bandurriaj.gui.viewer.views.LoadedCdView;
import com.axokoi.bandurriaj.model.*;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

   public LoadedCdController(TaggingDiscPopupView taggingDiscPopupView, ThreadPoolTaskExecutor executorService, UserConfigurationService userConfigurationService, PopUpDisplayer popUpDisplayer) {
      this.taggingDiscPopupView = taggingDiscPopupView;
      this.executorService = executorService;

      this.userConfigurationService = userConfigurationService;
      this.popUpDisplayer = popUpDisplayer;
   }

   public Disc saveCdOnCatalogue(Disc disc, Catalogue catalogue) {

      //Complete the discinfo
      //For the moment we only have MusicBrainz implemented so we just pick the only one in the set
      ExternalIdentifier externalIdentifier = disc.getExternalIdentifier().stream().findAny().orElseThrow(() -> new RuntimeException("Impossible to find the external indentifier for disc"));

      // Create the task for looking the metadata in the background
      Future<?> futureTaggedDiscs = executorService.submit(() -> {

         Disc loadedCd = taggingFacade.getDiscFromUniqueIdentifier(externalIdentifier).orElseThrow(() -> new RuntimeException("Impossible to tag cd with external identifier:" + externalIdentifier.getType() + ":" +
                 externalIdentifier.getIdentifier()));
         log.info("Cd tagged was: {}", externalIdentifier.getIdentifier());
         Platform.runLater(() -> ((Stage) taggingDiscPopupView.getScene().getWindow()).close());
         return loadedCd;
      });

      popUpDisplayer.displayNewPopupWithFunction(taggingDiscPopupView, null, () -> null);
      try {
         disc = (Disc) futureTaggedDiscs.get();
      } catch (InterruptedException | ExecutionException e) {
         throw new RuntimeException("Impossible to tag cd with external identifier:" + externalIdentifier.getType() + ":"+
                 externalIdentifier.getIdentifier());
      }

      Set<Artist> creditedArtistsToPersist = getArtistsToPersists(disc.getCreditedArtists());
      Set<Artist> relatedArtistToPersists = getArtistsToPersists(disc.getRelatedArtist());

      Optional<Disc> existingDisc = discService.findByNameIgnoreCase(disc.getName());
      Disc discToPersist = existingDisc.orElse(disc);
      catalogue.getDiscs().add(discToPersist);

      discToPersist.setCreditedArtists(creditedArtistsToPersist);
      discToPersist.setRelatedArtist(relatedArtistToPersists);
      persistsCdOnCatalogue(catalogue, creditedArtistsToPersist, relatedArtistToPersists, discToPersist);

      userConfigurationService.saveConfiguration(UserConfiguration.Keys.LAST_CATALOGUE_USED, catalogue.getId().toString());

      return discToPersist;
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
