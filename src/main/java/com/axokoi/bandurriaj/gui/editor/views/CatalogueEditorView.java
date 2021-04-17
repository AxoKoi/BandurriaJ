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

   private Label catalogueNameLabel;
   private TextField catalogueNameInput;

   public CatalogueEditorView(CatalogueEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      catalogueNameLabel = new Label(messagesProvider.getMessageFrom("catalogue.editor.view.name"));
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
      entityToEdit.setName(this.catalogueNameInput.getText());
      super.onSave(event);
   }

   @Override
   public CatalogueEditorView refresh(Catalogue catalogue) {
      entityToEdit = catalogue;
      catalogueNameInput.setText(catalogue.getName());
      return this;
   }


}
