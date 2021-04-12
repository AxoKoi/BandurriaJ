package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.CatalogueEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Catalogue;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.springframework.stereotype.Component;


@Component
public class CatalogueEditorView extends EditorView<Catalogue> {

   private final CatalogueEditorController controller;
   private final MessagesProvider messagesProvider;
   private Label catalogueNameLabel;
   private TextField catalogueNameInput;
   private Catalogue catalogueToEdit;

   public CatalogueEditorView(CatalogueEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);
      this.controller = controller;
      this.messagesProvider = messagesProvider;

      catalogueNameLabel = new Label("Name");
      catalogueNameInput = new TextField();

      GridPane center = new GridPane();
      center.add(catalogueNameLabel, 0, 0, 1, 1);
      center.add(catalogueNameInput, 1, 0, 1, 1);
      center.add(cancelButton, 0, 1, 1, 1);
      center.add(saveButton, 1, 1, 1, 1);
      this.setCenter(center);
   }

   @Override
   protected void onSave(ActionEvent event) {
      catalogueToEdit.setName(this.catalogueNameInput.getText());
      super.onSave(event);
   }

   @Override
   public void refresh(Catalogue catalogue) {
      catalogueToEdit = catalogue;
      catalogueNameInput.setText(catalogue.getName());
   }

   public Catalogue getEntityToEdit(){
      return this.catalogueToEdit;
   }

}
