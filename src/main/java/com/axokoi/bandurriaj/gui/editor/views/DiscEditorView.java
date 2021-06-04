package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.commons.cells.list.ArtistCell;
import com.axokoi.bandurriaj.gui.commons.cells.list.TrackCell;
import com.axokoi.bandurriaj.gui.commons.handlers.mouse.DoubleClickHandler;
import com.axokoi.bandurriaj.gui.commons.handlers.mouse.SinglePrimaryClickHandler;
import com.axokoi.bandurriaj.gui.editor.controllers.DiscEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
   private final String add;
   private final String delete;


   protected DiscEditorView(DiscEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);
      add = messagesProvider.getMessageFrom("button.add");
      delete = messagesProvider.getMessageFrom("button.delete");

      frontCoverEditButton = new Button(messagesProvider.getMessageFrom("disc.editor.view.edit.image"));
      frontCover.setFitHeight(250);
      frontCover.setFitWidth(250);

      var font = new Font(frontCoverEditButton.getFont().getFamily(), 20);

      var centerGridPane = new GridPane();
      centerGridPane.setAlignment(Pos.BOTTOM_CENTER);
      centerGridPane.setHgap(10);
      centerGridPane.setVgap(10);

      addArtists(messagesProvider, font, centerGridPane, "disc.editor.view.main.artists.label", 0, mainArtist);

      addArtists(messagesProvider, font, centerGridPane, "disc.editor.view.related.artists.label", 1, relatedArtist);

      addTracks(messagesProvider, font, centerGridPane);

      addCoverImage(messagesProvider, font, centerGridPane);

   //   centerGridPane.add(cancelButton, 0, 4, 1, 1);
      centerGridPane.add(saveButton, 1, 4, 1, 1);
      this.getChildren().add(centerGridPane);


      mainArtist.setOnMouseClicked(new DoubleClickHandler(controller::displayArtistEditor));
      mainArtist.setCellFactory(x -> new ArtistCell());

      relatedArtist.setOnMouseClicked(new DoubleClickHandler(controller::displayArtistEditor));
      relatedArtist.setCellFactory(x -> new ArtistCell());

      tracks.setOnMouseClicked(new DoubleClickHandler(controller::displayTrackEditor));
      tracks.setCellFactory(x -> new TrackCell());

      frontCoverEditButton.setOnMouseClicked(new SinglePrimaryClickHandler(event -> {
         var fileChooser = new FileChooser();
         var file = fileChooser.showOpenDialog(this.getScene().getWindow());
         try {
            if (file != null) {
               frontCover.setImage(new Image(new FileInputStream(file.getAbsolutePath())));
               entityToEdit.setPathToImage(file.getAbsolutePath());
            }
         } catch (Exception e) {
            log.error("Error when loading the image at:" + file.getAbsolutePath(), e);
         }
      }));
   }

   private void addCoverImage(MessagesProvider messagesProvider, Font font, GridPane center) {
      var frontCoverLabel = new Label(messagesProvider.getMessageFrom("disc.editor.view.front.cover.label"));
      frontCoverLabel.setFont(font);
      var frontCoverHBox = new HBox(frontCoverLabel, frontCoverEditButton);
      frontCoverHBox.setSpacing(10);
      center.add(frontCoverHBox, 1, 2, 1, 1);
      center.add(frontCover, 1, 3, 1, 1);
   }

   private void addTracks(MessagesProvider messagesProvider, Font font, GridPane center) {
      var tracksLabel = new Label(messagesProvider.getMessageFrom("disc.editor.view.tracks.label"));
      tracksLabel.setFont(font);
      var addTrackButton = new Button(add);
      addTrackButton.setOnAction(((DiscEditorController) controller)::addNewTrack);

      var deleteTrackButton = new Button(delete);
      deleteTrackButton.setOnAction(event -> ((DiscEditorController) controller).deleteTrack(event, tracks));

      var tracksHBox = new HBox(tracksLabel, addTrackButton, deleteTrackButton);
      tracksHBox.setSpacing(10);
      center.add(tracksHBox, 0, 2, 1, 1);
      center.add(tracks, 0, 3, 1, 1);
   }

   private void addArtists(MessagesProvider messagesProvider, Font font, GridPane center, String messageKey, int colIndex, ListView<Artist> mainArtist) {
      var mainArtistLabel = new Label(messagesProvider.getMessageFrom(messageKey));
      var mainArtistHBox = new HBox(mainArtistLabel);
      mainArtistHBox.setSpacing(10);
      mainArtistLabel.setFont(font);

      center.add(mainArtistHBox, colIndex, 0, 1, 1);
      center.add(mainArtist, colIndex, 1, 1, 1);
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
