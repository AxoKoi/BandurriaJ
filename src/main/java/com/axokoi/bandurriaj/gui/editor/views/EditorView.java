package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.EditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Searchable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class EditorView<S extends Searchable> extends VBox {

   private final EditorController<S> controller;
   protected final Button saveButton;
   protected final Button cancelButton;
   protected final MessagesProvider messagesProvider;
   protected S entityToEdit;

   protected EditorView(EditorController<S> controller, MessagesProvider messagesProvider) {
      this.controller = controller;
      this.messagesProvider = messagesProvider;

      saveButton = new Button(messagesProvider.getMessageFrom("button.save"));
      saveButton.setOnAction(this::onSave);
      cancelButton = new Button(messagesProvider.getMessageFrom("button.cancel"));
      cancelButton.setOnAction(this::onCancel);
   }

   protected void onCancel(ActionEvent actionEvent) {
      this.controller.onCancel(actionEvent);
      ((Stage) cancelButton.getScene().getWindow()).close();
   }


   protected void onSave(ActionEvent event) {
      this.controller.onSave(event);
      ((Stage) saveButton.getScene().getWindow()).close();
   }

   public S getEntityToEdit() {
      return entityToEdit;
   }

   public abstract EditorView<S> refresh(S searchable);
}
