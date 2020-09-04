package com.axokoi.BandurriaJ;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.model.Catalogue;
import com.axokoi.BandurriaJ.model.CatalogueRepository;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.model.DiscRepository;
import com.axokoi.BandurriaJ.model.GroupRepository;
import com.axokoi.BandurriaJ.model.Track;

@Component
public class DBCreation {

	@Autowired
	private DiscRepository discRepository;
	@Autowired
	private GroupRepository groupRepository;

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
		band.setName("group name:" + value);
		band.setComment("");
		Artist artist = new Artist();
		artist.setName("this is the artist name:" + value);
		List<Artist> artists = new ArrayList<>();
		artists.add(artist);
		band.setArtists(artists);
		testDisc.setBand(band);

		Track track = new Track();
		track.setName("Track 1:" + value);
		List<Track> tracks = new ArrayList<>();
		tracks.add(track);
		testDisc.setTracks(tracks);

		groupRepository.save(band);
		discRepository.save(testDisc);
		return testDisc;
	}
}
