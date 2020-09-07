package com.axokoi.BandurriaJ.Controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.model.BandRepository;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.views.BandView;

@Component
public class BandController {
	@Autowired
	BandRepository bandRepository;

	@Autowired
	BandView bandView;

	public void refreshView(Band band) {
		bandView.refresh(bandRepository.findById(band.getId()).orElseThrow());
	}

	@Transactional
	public List<Disc> findAllDiscByBand(Band band) {
		return bandRepository.findById(band.getId()).orElseThrow().getDiscs();
	}

	@Transactional
	public List<Artist> findAllArtistByBand(Band band) {
		return bandRepository.findById(band.getId()).orElseThrow().getArtists();
	}
}
