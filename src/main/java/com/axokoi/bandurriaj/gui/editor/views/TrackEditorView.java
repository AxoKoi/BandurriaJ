package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.CRUDTrackView;
import com.axokoi.bandurriaj.gui.editor.controllers.EditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Track;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;

@Component
public class TrackEditorView extends CRUDTrackView {


   protected TrackEditorView(EditorController<Track> controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);
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
