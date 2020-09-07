package com.axokoi.BandurriaJ.views;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.model.ArtistService;
import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.model.BandService;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.model.DiscService;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@Component
public class DiscView extends VBox {

	@Autowired
	DiscService discService;
	@Autowired
	BandService bandService;

	private final Label discName = new Label("Disc Name:");
	private final Label bandName = new Label("Group :");
	private List<Label> artists = new ArrayList<>();
	private List<Label> tracks = new ArrayList<>();

	public DiscView() {

		getChildren().add(discName);
		getChildren().add(bandName);
		getChildren().addAll(artists);
		getChildren().addAll(tracks);

		this.setPadding(new Insets(10));
		this.setSpacing(8);

	}

	public void refresh(Disc discToDisplay) {
		Disc disc = discService.findById(discToDisplay.getId());

		discName.setText("Name:" + disc.getName());
		tracks.clear();

		tracks.addAll(disc.getTracks().stream()
				//		.map(x -> trackRepository.findById(x.getId()))
				.map(x -> new Label(x.getName()))
				.collect(Collectors.toList()));

		Band band = bandService.findById(disc.getBand().getId());

		bandName.setText("Group :" + band.getName());
		List<Artist> artistList = band.getArtists();

		artistList = artistList.stream().map(x -> ArtistService.getById(x.getId()).get()).collect(Collectors.toList());

		artists = artistList.stream().map(x -> new Label(x.getName() + ":" + x.getRole()))
				.collect(Collectors.toList());

		this.getChildren().clear();
		this.getChildren().add(discName);
		this.getChildren().add(bandName);
		this.getChildren().addAll(artists);
		this.getChildren().addAll(tracks);

	}
}
