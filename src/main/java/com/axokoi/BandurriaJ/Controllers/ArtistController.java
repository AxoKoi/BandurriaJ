package com.axokoi.BandurriaJ.Controllers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.model.ArtistRepository;
import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.model.BandRepository;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.views.ArtistView;

@Component
public class ArtistController {

	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	BandRepository bandRepository;

	@Autowired
	ArtistView artistView;

	public void refreshView(Artist artist) {
		artistView.refresh(artistRepository.findById(artist.getId()).orElseThrow());
	}

	@Transactional
	public List<Disc> findAllDiscByArtist(Artist artist) {

		return IterableUtils.toList(bandRepository.findAll()).stream()
				.filter(band -> band.getArtists().stream().map(Artist::getId).anyMatch(x -> x.equals(artist.getId())))
				.map(Band::getDiscs)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
}
