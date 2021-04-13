package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.EditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Track;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


public class TrackEditorView extends EditorView<Track> {

   private final Label numberLabel;
   private final TextField numberText;
   private final Label nameLabel;
   private final TextField nameText;
   private final Label durationLabel;
   private final TextField durationText;
   private final Label commentLabel;
   private final TextField commentText;
   private Track trackToEdit;

   protected TrackEditorView(EditorController<Track> controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      numberLabel = new Label("#");
      numberText = new TextField();
      nameLabel = new Label("name:");
      nameText = new TextField();
      durationLabel = new Label("duration:");
      durationText = new TextField();
      commentLabel = new Label("comments:");
      commentText = new TextField();

      GridPane center = new GridPane();
      center.add(numberLabel, 0, 0, 1, 1);
      center.add(numberText, 1, 0, 1, 1);
      center.add(nameLabel, 0, 1, 1, 1);
      center.add(nameText, 1, 1, 1, 1);
      center.add(durationLabel, 0, 2, 1, 1);
      center.add(durationText, 1, 2, 1, 1);
      center.add(commentLabel, 0, 3, 1, 1);
      center.add(commentText, 0, 3, 1, 1);

      center.add(cancelButton, 0, 4, 1, 1);
      center.add(saveButton, 1, 4, 1, 1);
      this.setCenter(center);
   }

   @Override
   public EditorView<Track> refresh(Track track) {
      trackToEdit = track;

      numberText.setText(String.valueOf(track.getNumber()));
      nameText.setText(track.getName());
      durationText.setText(track.getDuration());
      commentText.setText(track.getComment());

      return this;
   }

   @Override
   protected void onSave(ActionEvent actionEvent) {
      trackToEdit.setNumber(Integer.parseInt(numberText.getText()));
      trackToEdit.setName(nameText.getText());
      trackToEdit.setDuration(durationText.getText());
      trackToEdit.setComment(commentText.getText());
      super.onSave(actionEvent);
   }

   public Track getEntityToEdit() {
      return trackToEdit;
   }
}
