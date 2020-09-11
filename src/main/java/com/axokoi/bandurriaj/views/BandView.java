package com.axokoi.bandurriaj.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.controllers.BandController;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

@Component
public class BandView extends VBox {
	@Autowired
	BandController bandController;

	private final Label name = new Label("Band Name: ");
	private final Label comment = new Label("Comments: ");

	private final ListView<Artist> artists = new ListView<>();
	private final ListView<Disc> discs = new ListView<>();

	public BandView() {
		getChildren().add(name);
		getChildren().addAll(comment);
		getChildren().addAll(artists);
		getChildren().addAll(discs);

		discs.setCellFactory(list -> new DiscCell());
		artists.setCellFactory(list -> new ArtistCell());
		this.setPadding(new Insets(10));
		this.setSpacing(8);
	}

	public void refresh(Band band) {
		Band bandToDisplay = bandController.fetchBandToDisplay(band);
		name.setText("Band name: " + bandToDisplay.getName());
		comment.setText("Comments: " + bandToDisplay.getComment());

		discs.getItems().clear();
		discs.getItems().addAll(FXCollections.observableArrayList(bandToDisplay.getDiscs()));

		artists.getItems().clear();
		artists.getItems().addAll(FXCollections.observableArrayList(bandToDisplay.getArtists()));

	}

	private static class DiscCell extends ListCell<Disc> {
		@Override
		protected void updateItem(Disc disc, boolean empty) {
			super.updateItem(disc, empty);

			setText(disc == null ? "" : disc.getName());
		}
	}

	private static class ArtistCell extends ListCell<Artist> {
		@Override
		protected void updateItem(Artist artist, boolean empty) {
			super.updateItem(artist, empty);

			setText(artist == null ? "" : artist.getName());
		}
	}

}
