package com.axokoi.BandurriaJ;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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
import com.axokoi.BandurriaJ.views.CatalogueView;

@Component
public class DBCreation {

	@Autowired
	private DiscRepository discRepository;
	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private CatalogueRepository catalogueRepository;

	public void init(){

		Catalogue cat1 = new Catalogue();
		cat1.setName("Catalogue 1");

		Catalogue cat2 = new Catalogue();
		cat2.setName("Catalogue 2");

		Disc testDisc = new Disc();
		testDisc.setName("test disc Name");

		Band band = new Band();
		band.setName("group name");
		band.setComment("");
		Artist artist = new Artist();
		artist.setName("this is the artist name");
		List<Artist> artists = new ArrayList<>();
		artists.add(artist);
		band.setArtists(artists);
		testDisc.setBand(band);

		Track track = new Track();
		track.setName("Track 1");
		List<Track> tracks = new ArrayList<>();
		testDisc.setTracks(tracks);
		groupRepository.save(band);
		discRepository.save(testDisc);
		List<Disc> discs1 = new ArrayList<>();
		discs1.add(testDisc);
		cat1.setDiscs(discs1);
		catalogueRepository.save(cat1);
		catalogueRepository.save(cat2);
	}
}
