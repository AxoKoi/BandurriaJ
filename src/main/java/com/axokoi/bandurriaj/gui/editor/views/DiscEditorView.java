package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.EditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Slf4j
@Component
public class DiscEditorView extends EditorView<Disc> {

   ListView<Artist> mainArtist = new ListView<>();
   ListView<Artist> relatedArtist = new ListView<>();
   ListView<Track> tracks = new ListView<>();
   ImageView frontCover = new ImageView();

   protected DiscEditorView(EditorController<Disc> controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      GridPane center = new GridPane();

      center.add(mainArtist, 0, 0, 1, 1);
      center.add(relatedArtist, 0, 1, 1, 1);
      center.add(tracks, 1, 0, 1, 1);
      center.add(frontCover, 1, 1, 1, 1);
      center.add(cancelButton,0,2,1,1);
      center.add(saveButton,1,2,1,1);
      this.setCenter(center);
   }

   @Override
   public EditorView<Disc> refresh(Disc disc) {
      entityToEdit = disc;
      //IRO Add hanlders to edit.
      mainArtist.getItems().clear();
      mainArtist.getItems().addAll(disc.getCreditedArtists());
//
      relatedArtist.getItems().clear();
      relatedArtist.getItems().addAll(disc.getRelatedArtist());
      tracks.getItems().clear();
      tracks.getItems().addAll(disc.getTracks());
      try {
         frontCover.setImage(new Image(new FileInputStream(disc.getPathToImage())));
      } catch (FileNotFoundException e) {
         log.error("Image to edit was not found:"+disc.getPathToImage(),e);
      }
      return this;
   }
}
