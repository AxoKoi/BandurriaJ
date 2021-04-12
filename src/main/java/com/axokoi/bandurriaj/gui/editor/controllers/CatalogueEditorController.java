package com.axokoi.bandurriaj.gui.editor.controllers;

import com.axokoi.bandurriaj.gui.editor.views.CatalogueEditorView;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.services.dataaccess.CatalogueService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogueEditorController extends EditorController<Catalogue> {

   @Autowired
   private CatalogueEditorView catalogueView;

   private final CatalogueService catalogueService;

   public CatalogueEditorController(CatalogueService catalogueService) {
      this.catalogueService = catalogueService;
   }


   @Override
   protected Node getView() {
      return this.catalogueView;
   }

   @Override
   protected void refreshView(Catalogue catalogue) {
      catalogueView.refresh(catalogue);
   }

   @Override
   public void onCancel(ActionEvent event) {

   }

   @Override
   public void onSave(ActionEvent event) {
      catalogueService.save(catalogueView.getEntityToEdit());
   }
}
