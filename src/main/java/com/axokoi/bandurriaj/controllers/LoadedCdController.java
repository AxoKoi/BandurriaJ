package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.model.*;
import com.axokoi.bandurriaj.views.LoadedCdView;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LoadedCdController {
   @Autowired
   DiscRepository discRepository;
   @Autowired
   CatalogueRepository catalogueRepository;
   @Autowired
   ArtistRepository artistRepository;
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
      List<Artist> artistsToPersist = artists.stream().map(x -> artistRepository.findByMbIdentifier(x.getMbIdentifier()).orElse(x)).collect(Collectors.toList());

      Optional<Disc> existingDisc = discRepository.findByNameIgnoreCase(disc.getName());
      Disc discToPersist = existingDisc.orElse(disc);

      discToPersist.setArtists(artistsToPersist);
      artistsToPersist.forEach(x->artistRepository.save(x));
      discRepository.save(discToPersist);

      catalogue.getDiscs().add(discToPersist);
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
