package com.axokoi.bandurriaj.gui.viewer.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import com.axokoi.bandurriaj.gui.viewer.views.*;

import javafx.scene.Node;

@Component
public class ArtistController extends ViewerController<Artist> {

	@Autowired
	ArtistService artistService;

	@Autowired
	DiscService discService;

	@Autowired
	ArtistView artistView;

	@Autowired
	DiscController discController;

	@Override
	public void refreshView(Artist artist) {
		artistView.refresh(artistService.findById(artist.getId()));
	}

	@Override
	protected Node getView() {
		return this.artistView;
	}

	public List<Disc> findAllDiscByArtist(Artist artist) {

		return discService.findAllDiscByArtist(artist);

	}

	public void replaceCenterWithDisc(Disc selectedItem) {
		discController.displayViewCenter(selectedItem);
	}

}