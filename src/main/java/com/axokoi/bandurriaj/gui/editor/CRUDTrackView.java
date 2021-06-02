package com.axokoi.bandurriaj.gui.editor;

import com.axokoi.bandurriaj.gui.editor.controllers.EditorController;
import com.axokoi.bandurriaj.gui.editor.views.EditorView;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Track;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.springframework.util.Assert;

public abstract class CRUDTrackView extends EditorView<Track> {

   protected final Label numberLabel = new Label();
   protected final TextField numberText = new TextField();
   protected final Label nameLabel = new Label();
   protected final TextField nameText = new TextField();
   protected final Label durationLabel = new Label();
   protected final TextField durationText = new TextField();
   protected final Label commentLabel = new Label();
   protected final TextField commentText = new TextField();
   protected final Label title = new Label();

   protected CRUDTrackView(EditorController<Track> controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      this.setAlignment(Pos.TOP_CENTER);

      title.setText(messagesProvider.getMessageFrom("track.editor.view.title"));
      title.setFont(new Font(title.getFont().getFamily(),20));
      this.getChildren().add(title);

      numberLabel.setText(messagesProvider.getMessageFrom("track.editor.view.number"));
      nameLabel.setText(messagesProvider.getMessageFrom("track.editor.view.name"));
      durationLabel.setText(messagesProvider.getMessageFrom("track.editor.view.duration"));
      commentLabel.setText(messagesProvider.getMessageFrom("track.editor.view.comments"));

      var center = new GridPane();
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
      Assert.notNull(track, "Track in view cannot be null!");
      entityToEdit = track;

      numberText.setText(String.valueOf(track.getNumber()));
      nameText.setText(track.getName());
      durationText.setText(track.getDuration());
      commentText.setText(track.getComment());

      return this;
   }

}

