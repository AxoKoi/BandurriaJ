package com.axokoi.bandurriaj.gui.viewer.views;


import com.axokoi.bandurriaj.gui.commons.cells.list.ArtistCell;
import com.axokoi.bandurriaj.gui.commons.handlers.key.EnterKeyHandler;
import com.axokoi.bandurriaj.gui.commons.handlers.mouse.DoubleClickHandler;
import com.axokoi.bandurriaj.gui.viewer.controllers.DiscController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.MusicGenre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

@Slf4j
@Component
public class DiscView extends VBox {


    private final DiscController discController;
    private final Label discNumber = new Label();
    private final TrackListView trackListView;
    private final MessagesProvider messagesProvider;
    private final Label discName = new Label();
    private final Label byLabel;
    private final Label creditedArtistLabel = new Label();
    private final Label discGenres = new Label();
    private final HBox header;
    private final HBox numberContainer;
    private final HBox editContainer;
    private final HBox deleteContainer;
    private ListView<Artist> artists = new ListView<>();
    private ImageView albumPicture = new ImageView();
    private HBox centralComponent = new HBox();
    private final Button editButton;
    private final Button deleteButton;



    public DiscView(DiscController discController, TrackListView trackListView, MessagesProvider messagesProvider){
        this.discController = discController;
        this.trackListView = trackListView;
        this.messagesProvider = messagesProvider;

        byLabel = new Label(messagesProvider.getMessageFrom("disc.view.by"));
        discName.setFont(new Font(discName.getFont().getFamily(),40));
        byLabel.setFont(new Font(discName.getFont().getFamily(),30));
        creditedArtistLabel.setFont(new Font(creditedArtistLabel.getFont().getFamily(),40));

        albumPicture.setFitHeight(250);
        albumPicture.setFitWidth(250);
        centralComponent.getChildren().addAll(artists,albumPicture);
        editButton = new Button(messagesProvider.getMessageFrom("disc.edit.button"));
        deleteButton = new Button(messagesProvider.getMessageFrom("disc.delete.button"));

        numberContainer = new HBox(discNumber);
        numberContainer.setAlignment(Pos.CENTER_LEFT);
        editContainer = new HBox(editButton);
        editContainer.setAlignment(Pos.CENTER_RIGHT);
        deleteContainer = new HBox(deleteButton);
        deleteContainer.setAlignment(Pos.CENTER_RIGHT);
        var region = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        var leftHeaderContainer = new VBox(editContainer, deleteContainer);
        leftHeaderContainer.setSpacing(8);
        header = new HBox(numberContainer, region, leftHeaderContainer);
        header.setAlignment(Pos.TOP_CENTER);

        VBox.setVgrow(trackListView, Priority.ALWAYS);
        this.setAlignment(Pos.TOP_CENTER);


        this.setPadding(new Insets(14));
        this.setSpacing(8);

    }

    public void refresh(Disc discToDisplay) {
        Disc disc = discController.fetchDiscToDisplay(discToDisplay);

        discName.setText(disc.getName());
        discNumber.setText(discToDisplay.getUserIdentifier().orElse(messagesProvider.getMessageFrom("disc.view.no.user.identifier")));
        creditedArtistLabel.setText(disc.getCreditedArtists().stream().map(Artist::getName).reduce("", (x, y) -> x + " " + y));
        discGenres.setText(disc.getGenres().stream().map(MusicGenre::getName).collect(Collectors.joining(" - ")));
        trackListView.refresh(disc.getTracks());
        artists = refreshArtistsView(disc);


        if (discToDisplay.getPathToImage()!= null && !discToDisplay.getPathToImage().isEmpty()) {
            try {
                albumPicture.setImage(new Image(new FileInputStream(discToDisplay.getPathToImage())));
            } catch (FileNotFoundException e) {
                log.error("Image not found at:" + discToDisplay.getPathToImage(),e);
            }
        } else {
            albumPicture.setImage(null);
        }

        editButton.setOnAction(event -> discController.displayEditorPopup(event,disc));
        deleteButton.setOnAction(event->discController.displayDeletePopup(event,disc));

        this.getChildren().clear();
        this.getChildren().add(header);
        this.getChildren().add(discName);
        this.getChildren().add(byLabel);
        this.getChildren().add(creditedArtistLabel);
        this.getChildren().add(discGenres);
        centralComponent.getChildren().clear();
        HBox.setHgrow(artists, Priority.ALWAYS);
        centralComponent.getChildren().addAll(artists, albumPicture);
        getChildren().add(centralComponent);
        this.getChildren().add(trackListView);

    }

    private ListView<Artist> refreshArtistsView(Disc disc) {

        ListView<Artist> newArtistsToDisplay = new ListView<>();
        newArtistsToDisplay.getItems().clear();
        newArtistsToDisplay.setCellFactory(x->new ArtistCell());
        newArtistsToDisplay.setOnKeyPressed(new EnterKeyHandler(event -> discController.replaceCenterWithArtist(newArtistsToDisplay.getSelectionModel().getSelectedItem())));
        newArtistsToDisplay.setOnMouseClicked(new DoubleClickHandler(x->discController.replaceCenterWithArtist(newArtistsToDisplay.getSelectionModel().getSelectedItem())));

        ObservableList<Artist> artistsToDisplay = FXCollections.observableArrayList(disc.getAllArtist());
        newArtistsToDisplay.getItems().addAll(artistsToDisplay);
        return newArtistsToDisplay;
    }



}
