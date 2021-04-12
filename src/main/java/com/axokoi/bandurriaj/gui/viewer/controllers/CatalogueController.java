package com.axokoi.bandurriaj.gui.viewer.controllers;

import com.axokoi.bandurriaj.gui.commons.PopUpDisplayer;
import com.axokoi.bandurriaj.gui.editor.controllers.CatalogueEditorController;
import com.axokoi.bandurriaj.gui.editor.views.EditorView;
import com.axokoi.bandurriaj.gui.viewer.views.CatalogueView;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.CatalogueRepository;
import com.axokoi.bandurriaj.model.Disc;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class CatalogueController extends ViewerController<Catalogue> {
   @Autowired
   private CatalogueRepository catalogueRepository;
   @Autowired
   private CatalogueView catalogueView;
   @Autowired
   private DiscController discController;
   @Autowired
   private CatalogueEditorController catalogueEditorController;
   @Autowired
   private PopUpDisplayer popUpDisplayer;

   public List<Catalogue> getCatalogues() {
      return IterableUtils.toList(catalogueRepository.findAll());
   }

   public void addNewCatalogue(String catalogueName) {
      Catalogue newCatalogue = new Catalogue();
      newCatalogue.setName(catalogueName);
      catalogueRepository.save(newCatalogue);
      catalogueView.refresh();
   }

   @Transactional
   public void deleteCatalogue(String catalogueName) {
      catalogueRepository.deleteByName(catalogueName);
      catalogueView.refresh();
   }

   public void focus(Catalogue catalogue) {
      catalogueView.focus(catalogue);
   }

   public void dispatchRefreshToController(Disc disc) {
      discController.displayViewCenter(disc);
   }

   @Override
   protected Node getView() {
      return this.catalogueView;
   }

   @Override
   protected void refreshView(Catalogue searchable) {
      catalogueView.refresh();
   }

   public void refreshView() {
      catalogueView.refresh();
   }

   public void displayEditMenu(ActionEvent event, Catalogue selectedCatalogue) {
      // if(event.getSource() typeof )
      EditorView<Catalogue> catalogueEditorView = catalogueEditorController.getView();
       catalogueEditorView = catalogueEditorView.refresh(selectedCatalogue);
      popUpDisplayer.displayNewPopup(catalogueEditorView, null);
   }
}
