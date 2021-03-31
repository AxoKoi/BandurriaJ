package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.DiscController;
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

    private final Label discName = new Label();
    private final Label byLabel = new Label("By");
    private final Label creditedArtistlabel = new Label();
    private ListView<Artist> artists = new ListView<>();


    public DiscView(DiscController discController, TrackListView trackListView){
        this.discController = discController;
        this.trackListView = trackListView;

        discName.setFont(new Font(discName.getFont().getFamily(),40));
        byLabel.setFont(new Font(discName.getFont().getFamily(),30));
        creditedArtistlabel.setFont(new Font(creditedArtistlabel.getFont().getFamily(),40));


        VBox.setVgrow(trackListView, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);
        getChildren().add(discName);
        getChildren().add(byLabel);
        getChildren().add(creditedArtistlabel);
        getChildren().addAll(artists);
        getChildren().add(this.trackListView);
        this.setPadding(new Insets(14));
        this.setSpacing(8);

    }

    public void refresh(Disc discToDisplay) {
        Disc disc = discController.fetchDiscToDisplay(discToDisplay);

        discName.setText(disc.getName());

        creditedArtistlabel.setText(disc.getCreditedArtists().stream().map(Artist::getName).reduce("", (x, y) -> x + " " + y));
        trackListView.refresh(disc.getTracks());

        refreshArtistsView(disc);
        VBox.setVgrow(artists,Priority.SOMETIMES);
        this.getChildren().clear();
        this.getChildren().add(discName);
        this.getChildren().add(byLabel);
        this.getChildren().add(creditedArtistlabel);
        this.getChildren().addAll(artists);
        this.getChildren().add(trackListView);
    }

    private void refreshArtistsView(Disc disc) {

        artists.getItems().clear();
        artists.setCellFactory(x-> new ListCell<>(){
            @Override
            protected void updateItem(Artist artist, boolean empty){
                super.updateItem(artist, empty);
                if(artist!= null) {
                    this.setText(artist.getName());
                }
            }
        });
        ObservableList<Artist> artistsToDisplay = FXCollections.observableArrayList(disc.getAllArtist());
        artists.getItems().addAll(artistsToDisplay);
    }
}
