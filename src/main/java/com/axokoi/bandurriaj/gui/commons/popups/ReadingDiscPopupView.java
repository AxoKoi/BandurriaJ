package com.axokoi.bandurriaj.gui.commons.popups;

import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;

@Component
public class ReadingDiscPopupView extends GridPane {

   private final MessagesProvider messagesProvider;

   public ReadingDiscPopupView(MessagesProvider messagesProvider) {
      this.messagesProvider = messagesProvider;
      this.setAlignment(Pos.BASELINE_CENTER);
      final Label messageLabel = new Label("Loading your CD, please wait");
      messageLabel.setFont(new Font(messageLabel.getFont().getFamily(),40));
      this.add(messageLabel, 0, 0, 2, 2);
   }

}
