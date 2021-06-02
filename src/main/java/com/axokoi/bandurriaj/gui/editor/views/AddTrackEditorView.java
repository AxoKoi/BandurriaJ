package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.CRUDTrackView;
import com.axokoi.bandurriaj.gui.editor.controllers.AddTrackEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.event.ActionEvent;
import org.springframework.stereotype.Component;

@Component
public class AddTrackEditorView extends CRUDTrackView {


   protected AddTrackEditorView(AddTrackEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);
   }

   @Override
   public void onSave(ActionEvent event) {
      entityToEdit.setNumber(Integer.parseInt(numberText.getText()));
      entityToEdit.setName(nameText.getText());
      entityToEdit.setDuration(durationText.getText());
      entityToEdit.setComment(commentText.getText());
      super.onSave(event);
   }

}
