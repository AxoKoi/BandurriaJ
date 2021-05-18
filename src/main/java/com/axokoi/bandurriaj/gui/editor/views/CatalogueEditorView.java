package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.CatalogueEditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Catalogue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.springframework.stereotype.Component;


@Component
public class CatalogueEditorView extends EditorView<Catalogue> {

   private Label catalogueNameLabel;
   private TextField catalogueNameInput;
   private final Label topTitle=new Label();

   public CatalogueEditorView(CatalogueEditorController controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);

      catalogueNameLabel = new Label(messagesProvider.getMessageFrom("catalogue.editor.view.name"));
      catalogueNameInput = new TextField();
      this.setAlignment(Pos.TOP_CENTER);

      topTitle.setFont(new Font(topTitle.getFont().getFamily(),20));
      topTitle.setText(messagesProvider.getMessageFrom("catalogue.editor.view.title"));
      this.getChildren().add(topTitle);

      GridPane center = new GridPane();
      center.setAlignment(Pos.BOTTOM_CENTER);
      center.setHgap(10);
      center.setVgap(10);
      center.add(catalogueNameLabel, 0, 0, 1, 1);
      center.add(catalogueNameInput, 1, 0, 1, 1);
      center.add(cancelButton, 0, 1, 1, 1);
      center.add(saveButton, 1, 1, 1, 1);
      this.getChildren().add(center);
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
