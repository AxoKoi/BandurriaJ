package com.axokoi.bandurriaj.Controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import com.axokoi.bandurriaj.views.ArtistView;

@Component
public class ArtistController {

	@Autowired
	ArtistService artistService;

	@Autowired
	DiscService discService;

	@Autowired
	ArtistView artistView;

	public void refreshView(Artist artist) {
		artistView.refresh(artistService.findById(artist.getId()));
	}

	@Transactional
	public List<Disc> findAllDiscByArtist(Artist artist) {

		return discService.findAllDiscByArtist(artist);
	}
}
