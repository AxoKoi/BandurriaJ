package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.DiscController;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DiscView extends VBox {


    private final DiscController discController;
    private final TrackListView trackListView;

    private final Label discName = new Label();
    private final Label artistLabel = new Label("-- Artist --");
    private List<Label> artists = new ArrayList<>();


    public DiscView(DiscController discController, TrackListView trackListView){
        this.discController = discController;
        this.trackListView = trackListView;

        discName.setFont(new Font(discName.getFont().getFamily(),40));

        VBox.setVgrow(trackListView, Priority.ALWAYS);
        this.setAlignment(Pos.CENTER);
        getChildren().add(discName);
        getChildren().add(artistLabel);
        getChildren().addAll(artists);
        getChildren().add(this.trackListView);
        this.setPadding(new Insets(14));
        this.setSpacing(8);

    }

    public void refresh(Disc discToDisplay) {
        Disc disc = discController.fetchDiscToDisplay(discToDisplay);

        discName.setText(disc.getName());
        trackListView.refresh(discToDisplay.getTracks());

        Set<Artist> artistList = discToDisplay.getArtists();

        artistList = artistList.stream()
                .map(discController::fetchArtistToDisplay)
                .collect(Collectors.toSet());

        artists = artistList.stream().map(x -> new Label(x.getName()))
                .collect(Collectors.toList());

        this.getChildren().clear();
        this.getChildren().add(discName);
        getChildren().add(artistLabel);
        getChildren().addAll(artists);
        getChildren().add(trackListView);
    }
}
