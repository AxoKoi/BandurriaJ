package com.axokoi.bandurriaj.gui.viewer.views;


import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.editor.controllers.DiscEditorController;
import com.axokoi.bandurriaj.gui.viewer.controllers.DiscController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Slf4j
@Component
public class DiscView extends VBox {


    private final DiscController discController;
    private final TrackListView trackListView;
    private final MessagesProvider messagesProvider;
    private final Label discName = new Label();
    private final Label byLabel;
    private final Label creditedArtistLabel = new Label();
    private ListView<Artist> artists = new ListView<>();
    private ImageView albumPicture = new ImageView();
    private HBox centralComponent = new HBox();
    private final Button editButton;



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

        editButton = new Button("Edit");

        VBox.setVgrow(trackListView, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);
        getChildren().add(editButton);
        getChildren().add(discName);
        getChildren().add(byLabel);
        getChildren().add(creditedArtistLabel);
        getChildren().add(centralComponent);
        getChildren().add(this.trackListView);

        this.setPadding(new Insets(14));
        this.setSpacing(8);

    }

    public void refresh(Disc discToDisplay) {
        Disc disc = discController.fetchDiscToDisplay(discToDisplay);

        discName.setText(disc.getName());

        creditedArtistLabel.setText(disc.getCreditedArtists().stream().map(Artist::getName).reduce("", (x, y) -> x + " " + y));
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

        this.getChildren().clear();
        getChildren().add(editButton);
        this.getChildren().add(discName);
        this.getChildren().add(byLabel);
        this.getChildren().add(creditedArtistLabel);
        centralComponent.getChildren().clear();
        HBox.setHgrow(artists, Priority.ALWAYS);
        centralComponent.getChildren().addAll(artists, albumPicture);
        getChildren().add(centralComponent);
        this.getChildren().add(trackListView);

    }

    private ListView<Artist> refreshArtistsView(Disc disc) {

        ListView<Artist> newArtistsToDisplay = new ListView<>();
        newArtistsToDisplay.getItems().clear();
        newArtistsToDisplay.setCellFactory(x-> new ListCell<>(){
            @Override
            protected void updateItem(Artist artist, boolean empty){
                super.updateItem(artist, empty);
                if(artist!= null) {
                    this.setText(artist.getName());
                }
            }
        });
        newArtistsToDisplay.addEventHandler(KeyEvent.KEY_PRESSED, event-> discController.replaceCenterWithArtist(newArtistsToDisplay.getSelectionModel().getSelectedItem()));
        newArtistsToDisplay.addEventHandler(MouseEvent.MOUSE_CLICKED, event-> discController.replaceCenterWithArtist(newArtistsToDisplay.getSelectionModel().getSelectedItem()));

        ObservableList<Artist> artistsToDisplay = FXCollections.observableArrayList(disc.getAllArtist());
        newArtistsToDisplay.getItems().addAll(artistsToDisplay);
        return newArtistsToDisplay;
    }
}
