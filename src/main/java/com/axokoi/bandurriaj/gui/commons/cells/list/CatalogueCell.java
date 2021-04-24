package com.axokoi.bandurriaj.gui.commons.cells.list;

import com.axokoi.bandurriaj.model.Catalogue;
import javafx.scene.control.ListCell;

public class CatalogueCell extends ListCell<Catalogue> {

   @Override
   protected void updateItem(Catalogue catalogue, boolean empty) {
      super.updateItem(catalogue, empty);
      if (catalogue == null) {
         disableProperty();
         setText("");
      } else {
         setText(catalogue.getName());
      }
   }

}
