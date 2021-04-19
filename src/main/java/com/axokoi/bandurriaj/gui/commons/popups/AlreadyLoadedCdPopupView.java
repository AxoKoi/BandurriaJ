package com.axokoi.bandurriaj.gui.commons.popups;

import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class AlreadyLoadedCdPopupView extends GridPane {
   private final MessagesProvider messagesProvider;
   Button cancel;
   Button ok;

   public AlreadyLoadedCdPopupView(MessagesProvider messagesProvider) {
      this.messagesProvider = messagesProvider;
      this.setAlignment(Pos.BASELINE_CENTER);
      final Label messageLabel = new Label(messagesProvider.getMessageFrom("already.loaded.disc.popup.view.message"));
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
      ((Stage) ok.getScene().getWindow()).close();
   }
}
