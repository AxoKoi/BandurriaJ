package com.axokoi.bandurriaj;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.BandRepository;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.CatalogueRepository;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.model.Track;

@Component
public class DBCreation {

	@Autowired
	private DiscRepository discRepository;
	@Autowired
	private BandRepository bandRepository;

	@Autowired
	private CatalogueRepository catalogueRepository;

	public void init() {

		Catalogue cat1 = new Catalogue();
		cat1.setName("Catalogue 1");

		Catalogue cat2 = new Catalogue();
		cat2.setName("Catalogue 2");

		Disc testDisc1 = createDisc(1);
		Disc testDisc2 = createDisc(2);

		List<Disc> discs1 = new ArrayList<>();
		discs1.add(testDisc1);
		discs1.add(testDisc2);
		cat1.setDiscs(discs1);
		catalogueRepository.save(cat1);
		catalogueRepository.save(cat2);
	}

	private Disc createDisc(int value) {
		Disc testDisc = new Disc();
		testDisc.setName("test disc Name:" + value);

		Band band = new Band();
		band.setName("Band name:" + value);
		band.setComment("");
		Artist artist = new Artist();
		artist.setName("this is the artist name:" + value);
		List<Artist> artists = new ArrayList<>();
		artists.add(artist);
		band.setArtists(artists);
		band.setDiscs(new ArrayList<>());
		band.getDiscs().add(testDisc);
		testDisc.setBand(band);

		Track track = new Track();
		track.setName("Track 1:" + value);
		List<Track> tracks = new ArrayList<>();
		tracks.add(track);
		testDisc.setTracks(tracks);

		bandRepository.save(band);
		discRepository.save(testDisc);
		return testDisc;
	}
}