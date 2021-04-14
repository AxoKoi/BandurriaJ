package com.axokoi.bandurriaj.gui.commons.cells.list;

import com.axokoi.bandurriaj.model.Catalogue;
import javafx.scene.control.ListCell;

public class CatalogueCell extends ListCell<Catalogue> {

      @Override
      protected void updateItem(Catalogue catalogue, boolean empty) {
         super.updateItem(catalogue, empty);
         setText(catalogue == null ? "" : catalogue.getName());
      }

}
