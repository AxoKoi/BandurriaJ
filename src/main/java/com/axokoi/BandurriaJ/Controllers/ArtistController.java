package com.axokoi.BandurriaJ.Controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.services.dataaccess.ArtistService;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.services.dataaccess.DiscService;
import com.axokoi.BandurriaJ.views.ArtistView;

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
