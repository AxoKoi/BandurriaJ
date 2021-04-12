package com.axokoi.bandurriaj.gui.viewer.controllers;

import javax.transaction.Transactional;

import com.axokoi.bandurriaj.gui.viewer.views.DiscView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;

import javafx.scene.Node;

@Component
public class DiscController extends ViewerController<Disc> {

	@Autowired
	private DiscView discView;
	@Autowired
	private DiscService discService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private ArtistController artistController;

	@Override
	protected Node getView() {
		return this.discView;
	}

	@Override
	public void refreshView(Disc disc) {
		discView.refresh(disc);
	}

	@Transactional
	public Disc fetchDiscToDisplay(Disc discToDisplay) {
		return discService.findById(discToDisplay.getId());
	}

	public Artist fetchArtistToDisplay(Artist artist) {
		return artistService.findById(artist.getId());
	}

   public void replaceCenterWithArtist(Artist selectedArtist) {
		artistController.displayViewCenter(selectedArtist);

	}
}