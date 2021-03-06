package com.axokoi.bandurriaj.gui.viewer.views;

import com.axokoi.bandurriaj.gui.commons.cells.list.DiscCell;
import com.axokoi.bandurriaj.gui.commons.handlers.mouse.DoubleClickHandler;
import com.axokoi.bandurriaj.gui.viewer.controllers.ArtistController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class ArtistView extends VBox {
	private final Label name;
	private final Label discBy ;
	private  ListView<Disc> discs = new ListView<>();
	private final Button editButton;

	@Autowired
	private ArtistController artistController;

	private final MessagesProvider messagesProvider;

	public ArtistView( MessagesProvider messagesProvider) {
		this.messagesProvider = messagesProvider;
		name = new Label();
		name.setFont(new Font(name.getFont().getFamily(),40));

		discBy = new Label(this.messagesProvider.getMessageFrom("artist.view.disc.by"));
		discBy.setFont(new Font(discBy.getFont().getFamily(),30));

		editButton = new Button("Edit");

      this.setAlignment(Pos.TOP_CENTER);


		this.setPadding(new Insets(14));
		this.setSpacing(8);
	}

	public void refresh(Artist artist) {
		name.setText(artist.getName());
		List<Disc> artistDiscs = artistController.findAllDiscByArtist(artist);
		discs = new ListView<>();
		discs.getItems().addAll(FXCollections.observableArrayList(artistDiscs));
		discs.setCellFactory(list -> new DiscCell());
		discs.addEventHandler(KeyEvent.KEY_PRESSED,event-> artistController.replaceCenterWithDisc(discs.getSelectionModel().getSelectedItem()));
		discs.setOnMouseClicked(new DoubleClickHandler(x->artistController.replaceCenterWithDisc(discs.getSelectionModel().getSelectedItem())));
		editButton.setOnAction(event->artistController.displayEditorPopup(event, artist));

		this.getChildren().clear();
		HBox header = new HBox(editButton);
		header.setAlignment(Pos.TOP_RIGHT);
		getChildren().add(header);
		getChildren().add(name);
		getChildren().add(discBy);
		getChildren().addAll(discs);

	}



}
