package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.model.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Comparator;

@Component
public class TrackListView extends VBox {
   private final Label title = new Label("Tracks");
   private  TableView<Track> tracks = new TableView<>();
   private final TableColumn<Track, String>  trackNumberColumn = new TableColumn<>("#");
   private final TableColumn<Track, String>  trackNameColumn = new TableColumn<>("Name");
   private final TableColumn<Track, String>  trackDurationColumn = new TableColumn<>("Duration");


   public TrackListView() {

      trackNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
      trackNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
      trackDurationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

      tracks.getColumns().addAll(trackNumberColumn, trackNameColumn, trackDurationColumn);

      this.getChildren().addAll(title, tracks);
      this.setPadding(new Insets(14));
      this.setSpacing(8);
   }

   public void refresh (Collection <Track> tracksToDisplay){

      ObservableList<Track> items = FXCollections.observableArrayList(tracksToDisplay).sorted(Comparator.comparingInt(Track::getNumber));

      tracks.getColumns().clear();
      tracks.getItems().clear();
      this.getChildren().remove(tracks);

      tracks = new TableView<>();
      tracks.getColumns().addAll(trackNumberColumn,trackNameColumn,trackDurationColumn);

      if(items.isEmpty()){
         return;
      }

      this.tracks.getItems().addAll(items);
      this.getChildren().add(tracks);
   }

}
