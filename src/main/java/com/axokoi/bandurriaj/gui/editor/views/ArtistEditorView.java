package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.ArtistEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Catalogue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;

@Component
public class ArtistEditorView extends EditorView<Artist> {
   GridPane center = new GridPane();
   Label artistMbIdentifier = new Label("MBIdentifier");
   Label artistNameLabel = new Label("Name:");
   TextField artistNameText = new TextField();
   Label artistCommentLabel = new Label("Comments:");
   TextField artistCommentText = new TextField();

   protected ArtistEditorView(ArtistEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      center.add(artistMbIdentifier, 0, 0, 2,1);
      center.add(artistNameLabel,0,1,1,1);
      center.add(artistNameText,1,1,1,1);
      center.add(artistCommentLabel,0,2,1,1);
      center.add(artistCommentText,1,2,1,1);
      center.add(cancelButton, 0, 3, 1, 1);
      center.add(saveButton, 2, 3, 1, 1);
      this.setCenter(center);
   }

   @Override
   public EditorView<Artist> refresh(Artist artist) {
      entityToEdit = artist;
      artistMbIdentifier.setText(artist.getMbIdentifier());
      artistNameText.setText(artist.getName());
      artistCommentText.setText(artist.getComment());
      return this;
   }

   @Override
   protected void onSave(ActionEvent actionEvent){
      entityToEdit.setName(artistNameText.getText());
      entityToEdit.setComment(artistCommentText.getText());
      super.onSave(actionEvent);
   }

}
