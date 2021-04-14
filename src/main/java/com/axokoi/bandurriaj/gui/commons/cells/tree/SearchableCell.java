package com.axokoi.bandurriaj.gui.commons.cells.tree;

import com.axokoi.bandurriaj.model.Searchable;
import javafx.scene.control.TreeCell;

public class SearchableCell extends TreeCell<Searchable> {

   @Override
   protected void updateItem(Searchable searchable, boolean empty) {
      super.updateItem(searchable, empty);
      setText(searchable == null ? "" : searchable.getName());
   }

}
