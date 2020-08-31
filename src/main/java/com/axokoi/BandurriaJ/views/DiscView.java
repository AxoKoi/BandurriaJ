package com.axokoi.BandurriaJ.views;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.model.ArtistService;
import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.model.BandService;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.model.DiscService;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

@Component
public class DiscView extends VBox {

	public void refresh(Disc discToDisplay) {
		discToDisplay = DiscService.findById(discToDisplay.getId()).get();
		VBox discInformationVBox = new VBox();
//todo add checks if values are null/empty
		Label discName = new Label("Name:" + discToDisplay.getName());
		Label bandName = new Label("Group :" + discToDisplay.getBand().getName());
		Band band = BandService.findById(discToDisplay.getBand().getId()).get();
		List<Artist> artistList = band.getArtists();
		artistList = artistList.stream().map(x -> ArtistService.getById(x.getId()).get()).collect(Collectors.toList());
		List<Label> artists = artistList.stream().map(x -> new Label(x.getName() + ":" + x.getRole()))
				.collect(Collectors.toList());

		discInformationVBox.getChildren().add(discName);
		discInformationVBox.getChildren().add(bandName);
		discInformationVBox.getChildren().addAll(artists);

		this.getChildren().clear();
		this.getChildren().add(discInformationVBox);
	}

}
