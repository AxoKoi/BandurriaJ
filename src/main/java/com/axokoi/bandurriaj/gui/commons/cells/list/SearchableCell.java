package com.axokoi.bandurriaj.gui.commons.cells.list;

import com.axokoi.bandurriaj.model.Searchable;
import javafx.scene.control.ListCell;

public class SearchableCell extends ListCell<Searchable> {

   @Override
   protected void updateItem(Searchable searchable, boolean empty) {
      super.updateItem(searchable, empty);
      setText(searchable == null ? "" : searchable.getName());
   }

}
