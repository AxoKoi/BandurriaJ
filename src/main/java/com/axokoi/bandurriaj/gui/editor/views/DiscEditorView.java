package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.commons.cells.list.ArtistCell;
import com.axokoi.bandurriaj.gui.commons.cells.list.TrackCell;
import com.axokoi.bandurriaj.gui.editor.controllers.DiscEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.util.Comparator;

@Slf4j
@Component
public class DiscEditorView extends EditorView<Disc> {

   private ListView<Artist> mainArtist = new ListView<>();
   private ListView<Artist> relatedArtist = new ListView<>();
   private ListView<Track> tracks = new ListView<>();
   private ImageView frontCover = new ImageView();
   private Button frontCoverEditButton;



   protected DiscEditorView(DiscEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      frontCoverEditButton = new Button(messagesProvider.getMessageFrom("disc.editor.view.edit.image"));
      frontCover.setFitHeight(250);
      frontCover.setFitWidth(250);

      GridPane center = new GridPane();

      center.add(mainArtist, 0, 0, 1, 1);
      center.add(relatedArtist, 1, 0, 1, 1);
      center.add(tracks, 0, 1, 1, 1);
      center.add(frontCover, 1, 1, 1, 1);
      center.add(cancelButton, 0, 2, 1, 1);
      center.add(saveButton, 1, 2, 1, 1);
      this.setCenter(center);

      //IRO probably to add a right click edit menu?
      mainArtist.setOnMouseClicked(controller::displayArtistEditor);
    //IRO We could extract these cells to a common library
      mainArtist.setCellFactory(x-> new ArtistCell());

      relatedArtist.setOnMouseClicked(controller::displayArtistEditor);
      relatedArtist.setCellFactory(x-> new ArtistCell());

      tracks.setOnMouseClicked(controller::displayTrackEditor);

      tracks.setCellFactory(x->new TrackCell());

      frontCover.setOnMouseClicked(event->{
         FileChooser fileChooser = new FileChooser();
         File file = fileChooser.showOpenDialog(this.getScene().getWindow());
         try {
            frontCover.setImage(new Image(new FileInputStream(file.getAbsolutePath())));
            entityToEdit.setPathToImage(file.getAbsolutePath());
         } catch (Exception e) {
            log.error("Error when loading the image at:"+ file.getAbsolutePath(),e);
         }
      });

   }

   @Override
   public EditorView<Disc> refresh(Disc disc) {
      entityToEdit = disc;

      mainArtist.getItems().clear();
      mainArtist.getItems().addAll(disc.getCreditedArtists());

      relatedArtist.getItems().clear();
      relatedArtist.getItems().addAll(disc.getRelatedArtist());
      tracks.getItems().clear();
      tracks.getItems().addAll(disc.getTracks());
      tracks.getItems().sort(Comparator.comparingInt(Track::getNumber));
      try {
         frontCover.setImage(new Image(new FileInputStream(disc.getPathToImage())));
      } catch (Exception e) {
         log.error("Image to edit was not found:" + disc.getPathToImage(), e);
      }
      return this;
   }

}
