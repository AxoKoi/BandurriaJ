package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.model.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.util.Collection;

public class TrackListView extends VBox {
   private final Label title = new Label("Tracks");
   private final ListView<Track> tracks = new ListView<>();


   public TrackListView() {
      this.getChildren().addAll(title, tracks);
      this.setPadding(new Insets(14));
      this.setSpacing(8);
   }

   public void refresh (Collection <Track> tracksToDisplay){

      ObservableList<Track> items = FXCollections.observableArrayList(tracksToDisplay);
      this.tracks.getItems().clear();
      if(items.isEmpty()){
         return;
      }
      this.tracks.getItems().addAll(items);
      this.tracks.setCellFactory(list -> new TrackListView.TrackCell());
   }


   public static class TrackCell extends ListCell<Track> {
      @Override
      protected void updateItem(Track track, boolean empty) {
         super.updateItem(track, empty);
         setText(track == null ? "" : track.getId().toString() + " /t" + track.getName() + "/t" + track.getDuration() );
      }
   }

}
