package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.DiscController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

@Component
public class DiscView extends VBox {


    private final DiscController discController;
    private final TrackListView trackListView;
    private final MessagesProvider messagesProvider;
    private final Label discName = new Label();
    private final Label byLabel;
    private final Label creditedArtistLabel = new Label();
    private ListView<Artist> artists = new ListView<>();


    public DiscView(DiscController discController, TrackListView trackListView, MessagesProvider messagesProvider){
        this.discController = discController;
        this.trackListView = trackListView;
        this.messagesProvider = messagesProvider;
        byLabel = new Label(messagesProvider.getMessageFrom("disc.view.by"));
        discName.setFont(new Font(discName.getFont().getFamily(),40));
        byLabel.setFont(new Font(discName.getFont().getFamily(),30));
        creditedArtistLabel.setFont(new Font(creditedArtistLabel.getFont().getFamily(),40));


        VBox.setVgrow(trackListView, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);
        getChildren().add(discName);
        getChildren().add(byLabel);
        getChildren().add(creditedArtistLabel);
        getChildren().addAll(artists);
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

        VBox.setVgrow(artists,Priority.SOMETIMES);
        this.getChildren().clear();
        this.getChildren().add(discName);
        this.getChildren().add(byLabel);
        this.getChildren().add(creditedArtistLabel);

        this.getChildren().addAll(artists);
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
        ObservableList<Artist> artistsToDisplay = FXCollections.observableArrayList(disc.getAllArtist());
        newArtistsToDisplay.getItems().addAll(artistsToDisplay);
        return newArtistsToDisplay;
    }
}
