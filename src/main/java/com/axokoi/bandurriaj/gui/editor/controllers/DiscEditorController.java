package com.axokoi.bandurriaj.gui.editor.controllers;

import com.axokoi.bandurriaj.gui.editor.views.DiscEditorView;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;

public class DiscEditorController extends EditorController<Disc> {

   @Autowired
   private DiscEditorView discEditorView;
   private final DiscService discService;

   public DiscEditorController(DiscService discService) {
      this.discService = discService;
   }

   @Override
   protected Node getView() {
      return discEditorView;
   }

   @Override
   protected void refreshView(Disc disc) {
      discEditorView.refresh(disc);
   }

   @Override
   public void onCancel(ActionEvent event) {

   }

   @Override
   public void onSave(ActionEvent event) {
      discService.save(discEditorView.getEntityToEdit());
   }
}
