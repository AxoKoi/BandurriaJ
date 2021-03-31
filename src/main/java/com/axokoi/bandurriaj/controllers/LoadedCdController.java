package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.model.*;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import com.axokoi.bandurriaj.views.LoadedCdView;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class LoadedCdController {
   @Autowired
   DiscService discService;
   @Autowired
   CatalogueRepository catalogueRepository;
   @Autowired
   ArtistService artistService;
   @Autowired
   DiscController discController;
   @Autowired
   CatalogueController catalogueController;
   @Autowired
   LoadedCdView loadedCdView;

   public void saveCdOnCatalogue(Disc disc, Catalogue catalogue) {

      Set<Artist> creditedArtistsToPersist = getArtistsToPersists(disc.getCreditedArtists());
      Set<Artist> relatedArtistToPersists = getArtistsToPersists(disc.getRelatedArtist());

      Optional<Disc> existingDisc = discService.findByNameIgnoreCase(disc.getName());
      Disc discToPersist = existingDisc.orElse(disc);
      catalogue.getDiscs().add(discToPersist);

      discToPersist.setCreditedArtists(creditedArtistsToPersist);
      discToPersist.setRelatedArtist(relatedArtistToPersists);
      persistsCdOnCatalogue(catalogue, creditedArtistsToPersist,relatedArtistToPersists, discToPersist);
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
