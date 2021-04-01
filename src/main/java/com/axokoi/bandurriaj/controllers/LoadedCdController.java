package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.model.*;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import com.axokoi.bandurriaj.services.tagging.TaggingFacade;
import com.axokoi.bandurriaj.views.LoadedCdView;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LoadedCdController {
   final DiscService discService;
   final CatalogueRepository catalogueRepository;
   final ArtistService artistService;
   final DiscController discController;
   final CatalogueController catalogueController;
   final LoadedCdView loadedCdView;
   final TaggingFacade taggingFacade;

   public LoadedCdController(DiscService discService, CatalogueRepository catalogueRepository, ArtistService artistService, DiscController discController, CatalogueController catalogueController, LoadedCdView loadedCdView, TaggingFacade taggingFacade) {
      this.discService = discService;
      this.catalogueRepository = catalogueRepository;
      this.artistService = artistService;
      this.discController = discController;
      this.catalogueController = catalogueController;
      this.loadedCdView = loadedCdView;
      this.taggingFacade = taggingFacade;
   }

   public Disc saveCdOnCatalogue(Disc disc, Catalogue catalogue) {

      //Complete the discinfo
      //For the moment we only have MusicBrainz implemented so we just pick the only one in the set
      ExternalIdentifier externalIdentifier = disc.getExternalIdentifier().stream().findAny().orElseThrow(() -> new RuntimeException("Impossible to find the external indentifier for disc"));
      disc = taggingFacade.getDiscFromUniqueIdentifier(externalIdentifier.getIdentifier()).orElseThrow(()->new RuntimeException("Impossible to tag cd:"));

      Set<Artist> creditedArtistsToPersist = getArtistsToPersists(disc.getCreditedArtists());
      Set<Artist> relatedArtistToPersists = getArtistsToPersists(disc.getRelatedArtist());

      Optional<Disc> existingDisc = discService.findByNameIgnoreCase(disc.getName());
      Disc discToPersist = existingDisc.orElse(disc);
      catalogue.getDiscs().add(discToPersist);

      discToPersist.setCreditedArtists(creditedArtistsToPersist);
      discToPersist.setRelatedArtist(relatedArtistToPersists);
      persistsCdOnCatalogue(catalogue, creditedArtistsToPersist,relatedArtistToPersists, discToPersist);
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
}
