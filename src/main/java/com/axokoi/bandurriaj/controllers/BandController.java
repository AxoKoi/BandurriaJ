package com.axokoi.bandurriaj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.BandService;
import com.axokoi.bandurriaj.views.BandView;

import javafx.scene.Node;

@Component
public class BandController extends GuiController<Band> {
	@Autowired
	BandView bandView;
	@Autowired
	BandService bandService;
	@Autowired
	DiscController discController;
	@Autowired
	ArtistController artistController;

	@Override
	Node getView() {
		return this.bandView;
	}

	@Override
	public void refreshView(Band band) {
		bandView.refresh(bandService.findById(band.getId()));
	}

	public Band fetchBandToDisplay(Band band) {
		return bandService.findById(band.getId());
	}

	public void dispatchRefreshToController(Disc selectedDisc) {
		discController.displayViewCenter(selectedDisc);
	}

	public void dispatchRefreshToController(Artist selectedArtist) {
		artistController.displayViewCenter(selectedArtist);
	}
}
