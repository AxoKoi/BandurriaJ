package com.axokoi.bandurriaj.views;

import java.util.List;

import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

@Component
public final class ArtistView extends VBox {
	private final Label name;
	private final Label discBy ;
	private final ListView<Disc> discs = new ListView<>();

	@Autowired
	private ArtistController artistController;

	private final MessagesProvider messagesProvider;

	public ArtistView(MessagesProvider messagesProvider) {
		this.messagesProvider = messagesProvider;
		name = new Label();
		name.setFont(new Font(name.getFont().getFamily(),40));

		discBy = new Label(this.messagesProvider.getMessageFrom("artist.view.disc.by"));
		discBy.setFont(new Font(discBy.getFont().getFamily(),30));

      this.setAlignment(Pos.CENTER);
		getChildren().add(name);
		getChildren().add(discBy);
		getChildren().addAll(discs);

		discs.setCellFactory(list -> new DiscCell());

		discs.addEventHandler(KeyEvent.KEY_PRESSED,event-> artistController.replaceCenterWithDisc(discs.getSelectionModel().getSelectedItem()));
		discs.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> artistController.replaceCenterWithDisc(discs.getSelectionModel().getSelectedItem()));
		this.setPadding(new Insets(14));
		this.setSpacing(8);
	}

	public void refresh(Artist artist) {
		name.setText(artist.getName());
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
