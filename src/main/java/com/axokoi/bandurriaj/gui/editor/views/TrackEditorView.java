package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.CRUDTrackView;
import com.axokoi.bandurriaj.gui.editor.controllers.TrackEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;

@Component
public class TrackEditorView extends CRUDTrackView {


   protected TrackEditorView(TrackEditorController controller, MessagesProvider messagesProvider) {
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
