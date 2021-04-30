package com.axokoi.bandurriaj.gui.commons.popups;

import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Disc;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;


@Component
public class DeleteDiscWarningPopupView extends GridPane {
   private final MessagesProvider messagesProvider;
   private final DeleteDiscWarningPopupController controller;
   private final Button cancel;
   private final Button ok;
   private final  Label messageLabel = new Label();
   private Disc discToDelete;

   public DeleteDiscWarningPopupView(MessagesProvider messagesProvider, DeleteDiscWarningPopupController controller) {
      this.messagesProvider = messagesProvider;
      this.controller = controller;

      this.setAlignment(Pos.BASELINE_CENTER);

      messageLabel.setFont(new Font(messageLabel.getFont().getFamily(), 25));
      this.add(messageLabel, 0, 0, 2, 2);

      cancel = new Button(messagesProvider.getMessageFrom("button.cancel"));
      cancel.setOnAction(this::onCancel);
      this.add(cancel, 0, 2, 1, 1);

      ok = new Button(messagesProvider.getMessageFrom("button.ok"));
      ok.setOnAction(this::onOk);
      this.add(ok, 2, 2, 1, 1);

   }

   private void onCancel(ActionEvent actionEvent) {
      ((Stage) cancel.getScene().getWindow()).close();
   }


   protected void onOk(ActionEvent actionEvent) {
      controller.onOk(actionEvent);
      ((Stage) ok.getScene().getWindow()).close();
   }

   public void refreshView(Disc disc) {
      messageLabel.setText(messagesProvider.getMessageFrom("delete.disc.warning.popup.view.message",disc.getName()));
      this.discToDelete = disc;
   }

   public Disc getEntityToDelete() {
      return this.discToDelete;
   }
}
