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

   Artist artistToEdit;

   protected ArtistEditorView(ArtistEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      center.add(artistMbIdentifier, 0, 0, 1,2);
      center.add(artistNameLabel,1,0,1,1);
      center.add(artistNameText,1,1,1,1);
      center.add(artistCommentLabel,2,0,1,1);
      center.add(artistCommentText,1,1,1,1);

      this.setCenter(center);
   }

   @Override
   public EditorView<Artist> refresh(Artist artist) {
      artistToEdit = artist;
      artistMbIdentifier.setText(artist.getMbIdentifier());
      artistNameLabel.setText(artist.getName());
      artistCommentText.setText(artist.getComment());
      return this;
   }

   @Override
   protected void onSave(ActionEvent actionEvent){
      artistToEdit.setName(artistNameText.getText());
      artistToEdit.setComment(artistCommentText.getText());
      super.onSave(actionEvent);
   }

   public Artist getEntityToEdit(){
      return this.artistToEdit;
   }
}
