package com.axokoi.bandurriaj.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.controllers.ArtistController;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

@Component
public final class ArtistView extends VBox {
	private final Label name = new Label("Artist Name: ");
	private final Label role = new Label("Role: ");
	private final Label comment = new Label("Comments: ");
	private final ListView<Disc> discs = new ListView<>();

	@Autowired
	ArtistController artistController;

	public ArtistView() {

		getChildren().add(name);
		getChildren().add(role);
		getChildren().addAll(comment);
		getChildren().addAll(discs);

		discs.setCellFactory(list -> new DiscCell());
		this.setPadding(new Insets(10));
		this.setSpacing(8);
		this.setStyle("-fx-font-size: 12;");
	}

	public void refresh(Artist artist) {
		name.setText("Artist name: " + artist.getName());
		role.setText("Role: " + artist.getRole());
		comment.setText("Comments: " + artist.getComment());
		List<Disc> artistDiscs = artistController.findAllDiscByArtist(artist);
		discs.getItems().clear();
		discs.getItems().addAll(FXCollections.observableArrayList(artistDiscs));
	}

	private static class DiscCell extends ListCell<Disc> {
		@Override
		protected void updateItem(Disc disc, boolean empty) {
			super.updateItem(disc, empty);

			setText(disc == null ? "" : disc.getName());
		}
	}

}
