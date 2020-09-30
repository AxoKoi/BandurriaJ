package com.axokoi.bandurriaj.controllers;

import com.axokoi.bandurriaj.model.*;
import com.axokoi.bandurriaj.views.LoadedCdView;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoadedCdController {
    @Autowired
    DiscRepository discRepository;
    @Autowired
    CatalogueRepository catalogueRepository;
    @Autowired
    BandRepository bandRepository;
    @Autowired
    DiscController discController;
    @Autowired
    CatalogueController catalogueController;
    @Autowired
    LoadedCdView loadedCdView;

    public void saveCdOnCatalogue(Disc disc, Catalogue catalogue) {
        Band band = disc.getBand();
        Optional<Band> existingBand = bandRepository.findByNameIgnoreCase(band.getName());
        Band bandToPersist = existingBand.orElse(disc.getBand());

        Optional<Disc> existingDisc = discRepository.findByNameIgnoreCase(disc.getName());
        Disc discToPersist = existingDisc.orElse(disc);

        discToPersist.setBand(bandToPersist);
        bandRepository.save(bandToPersist);
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
