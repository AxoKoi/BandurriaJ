package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.DiscController;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DiscView extends VBox {


    private final DiscController discController;
    private final TrackListView trackListView;

    private final Label discName = new Label();
    private final Label artistLabel = new Label("Artists");
    private TreeView<Artist> artists = new TreeView<>();


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
        trackListView.refresh(disc.getTracks());

        artists = buildArtistsView(disc);
        VBox.setVgrow(artists,Priority.SOMETIMES);
        this.getChildren().clear();
        this.getChildren().add(discName);
        this.getChildren().add(artistLabel);
        this.getChildren().addAll(artists);
        this.getChildren().add(trackListView);
    }

    private TreeView<Artist> buildArtistsView(Disc disc) {
        //Todo Can't we avoid to create a new treeview each time? Maybe extract this to a listArtistView
        //IRO Now it's nod needed, Change it to List view instead
        artists = new TreeView<>();
        artists.setCellFactory(x-> new TreeCell<>(){
            @Override
            protected void updateItem(Artist artist, boolean empty){
                super.updateItem(artist, empty);
                if(artist!= null) {
                    this.setText(artist.getName());
                }
            }
        });
        List<TreeItem<Artist>> creditedArtists = disc.getCreditedArtists().stream().map(TreeItem::new).collect(Collectors.toList());
        creditedArtists.addAll(disc.getRelatedArtist().stream().map(TreeItem::new).collect(Collectors.toList()));

        TreeItem<Artist> root = new TreeItem<>();
        root.getChildren().addAll(creditedArtists);
        artists.setRoot(root);
        artists.setShowRoot(false);
        return artists;
    }
}
