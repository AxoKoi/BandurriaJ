package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.ArtistEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

@Component
public class ArtistEditorView extends EditorView<Artist> {
   GridPane center = new GridPane();
   Label topTitle = new Label();
   Label artistMbIdentifier = new Label("MBIdentifier");
   Label artistNameLabel = new Label("Name:");
   TextField artistNameText = new TextField();
   Label artistCommentLabel = new Label("Comments:");
   TextField artistCommentText = new TextField();

   protected ArtistEditorView(ArtistEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);



      artistMbIdentifier.setText(messagesProvider.getMessageFrom("artist.editor.view.mbidentifier"));
      artistMbIdentifier.setText(messagesProvider.getMessageFrom("artist.editor.view.name"));
      artistMbIdentifier.setText(messagesProvider.getMessageFrom("artist.editor.view.comments"));

      this.setAlignment(Pos.TOP_CENTER);

      topTitle.setFont(new Font(topTitle.getFont().getFamily(),20));
      this.getChildren().add(topTitle);

      center.setAlignment(Pos.BOTTOM_CENTER);
      center.setHgap(10);
      center.setVgap(10);
      center.add(artistNameLabel,0,1,1,1);
      center.add(artistNameText,1,1,2,1);
      center.add(artistCommentLabel,0,2,1,1);
      center.add(artistCommentText,1,2,2,1);
      center.add(cancelButton, 0, 3, 1, 1);
      center.add(saveButton, 2, 3, 1, 1);
      this.getChildren().add(center);
   }

   @Override
   public EditorView<Artist> refresh(Artist artist) {
      entityToEdit = artist;
      artistNameText.setText(artist.getName());
      artistCommentText.setText(artist.getComment());
      topTitle.setText(messagesProvider.getMessageFrom("artist.editor.view.title",artist.getMbIdentifier()));
      return this;
   }

   @Override
   protected void onSave(ActionEvent actionEvent){
      entityToEdit.setName(artistNameText.getText());
      entityToEdit.setComment(artistCommentText.getText());
      super.onSave(actionEvent);
   }

}
