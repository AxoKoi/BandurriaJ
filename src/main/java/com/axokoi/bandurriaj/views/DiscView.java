package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.DiscController;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiscView extends VBox {

    @Autowired
    DiscController discController;

    private final Label discName = new Label("Disc Name:");
    private final Label bandName = new Label("Group :");
    private final Label artistLabel = new Label("-- Artist --");
    private final Label tracksLabel = new Label("-- Tracks --");
    private List<Label> artists = new ArrayList<>();
    private final List<Label> tracks = new ArrayList<>();

    public DiscView() {

        getChildren().add(discName);
        getChildren().add(bandName);
        getChildren().add(artistLabel);
        getChildren().addAll(artists);
        getChildren().add(tracksLabel);
        getChildren().addAll(tracks);

        this.setPadding(new Insets(14));
        this.setSpacing(8);

    }

    public void refresh(Disc discToDisplay) {
        Disc disc = discController.fetchDiscToDisplay(discToDisplay);

        discName.setText("Name : " + disc.getName());
        tracks.clear();
// todo we can change this to a list with a specific cell view!
        tracks.addAll(disc.getTracks().stream()
                //		.map(x -> trackRepository.findById(x.getId()))
                .map(x -> new Label(x.getName()))
                .collect(Collectors.toList()));

        Band band = discController.fetchBandToDisplay(disc.getBand());

        bandName.setText("Group : " + band.getName());
        List<Artist> artistList = band.getArtists();

        artistList = artistList.stream()
                .map(x -> discController.fetchArtistToDisplay(x))
                .collect(Collectors.toList());

        artists = artistList.stream().map(x -> new Label(x.getName() + ":" + x.getRole()))
                .collect(Collectors.toList());

        this.getChildren().clear();
        this.getChildren().add(discName);
        this.getChildren().add(bandName);
        getChildren().add(artistLabel);
        getChildren().addAll(artists);
        getChildren().add(tracksLabel);
        this.getChildren().addAll(tracks);

    }
}
