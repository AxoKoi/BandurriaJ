package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.EditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Track;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

@Component
public class TrackEditorView extends EditorView<Track> {

   private final Label numberLabel;
   private final TextField numberText;
   private final Label nameLabel;
   private final TextField nameText;
   private final Label durationLabel;
   private final TextField durationText;
   private final Label commentLabel;
   private final TextField commentText;
   private final Label title = new Label();

   protected TrackEditorView(EditorController<Track> controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      numberLabel = new Label(messagesProvider.getMessageFrom("track.editor.view.number"));
      numberText = new TextField();
      nameLabel = new Label(messagesProvider.getMessageFrom("track.editor.view.name"));
      nameText = new TextField();
      durationLabel = new Label(messagesProvider.getMessageFrom("track.editor.view.duration"));
      durationText = new TextField();
      commentLabel = new Label(messagesProvider.getMessageFrom("track.editor.view.comments"));
      commentText = new TextField();

      this.setAlignment(Pos.TOP_CENTER);

      title.setText(messagesProvider.getMessageFrom("track.editor.view.title"));
      title.setFont(new Font(title.getFont().getFamily(),20));
      this.getChildren().add(title);

      GridPane center = new GridPane();
      center.setAlignment(Pos.BOTTOM_CENTER);
      center.setHgap(10);
      center.setVgap(10);
      center.add(numberLabel, 0, 0, 1, 1);
      center.add(numberText, 1, 0, 1, 1);
      center.add(nameLabel, 0, 1, 1, 1);
      center.add(nameText, 1, 1, 1, 1);
      center.add(durationLabel, 0, 2, 1, 1);
      center.add(durationText, 1, 2, 1, 1);
      center.add(commentLabel, 0, 3, 1, 1);
      center.add(commentText, 1, 3, 1, 1);

      center.add(cancelButton, 0, 4, 1, 1);
      center.add(saveButton, 1, 4, 1, 1);
      this.getChildren().add(center);
   }

   @Override
   public EditorView<Track> refresh(Track track) {
      entityToEdit = track;

      numberText.setText(String.valueOf(track.getNumber()));
      nameText.setText(track.getName());
      durationText.setText(track.getDuration());
      commentText.setText(track.getComment());

      return this;
   }

   @Override
   protected void onSave(ActionEvent actionEvent) {
      entityToEdit.setNumber(Integer.parseInt(numberText.getText()));
      entityToEdit.setName(nameText.getText());
      entityToEdit.setDuration(durationText.getText());
      entityToEdit.setComment(commentText.getText());
      super.onSave(actionEvent);
   }

}
