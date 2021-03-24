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
import java.util.List;
import java.util.Optional;
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
      List<Artist> artists = disc.getArtists();

      //Check if artists already exists
      //Quid if MBidentifier is empty?
      artists.forEach(x -> Assert.notNull(x.getMbIdentifier(), "MBIdentifier can't be null"));
      List<Artist> artistsToPersist = artists.stream().map(x -> artistService.findByMbIdentifier(x.getMbIdentifier()).orElse(x)).collect(Collectors.toList());

      Optional<Disc> existingDisc = discService.findByNameIgnoreCase(disc.getName());
      Disc discToPersist = existingDisc.orElse(disc);
      catalogue.getDiscs().add(discToPersist);
      discToPersist.setArtists(artistsToPersist);
      persistsCdOnCatalogue(catalogue, artistsToPersist, discToPersist);
   }

   @Transactional
   protected void persistsCdOnCatalogue(Catalogue catalogue, List<Artist> artistsToPersist, Disc discToPersist) {
      artistsToPersist.forEach(x -> artistService.save(x));
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
