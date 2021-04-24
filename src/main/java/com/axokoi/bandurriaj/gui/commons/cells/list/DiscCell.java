package com.axokoi.bandurriaj.gui.commons.cells.list;

import com.axokoi.bandurriaj.model.Disc;
import javafx.scene.control.ListCell;

public class DiscCell extends ListCell<Disc> {

   @Override
   protected void updateItem(Disc disc, boolean empty) {
      super.updateItem(disc, empty);
      if (disc == null) {
         disableProperty();
         setText("");
      } else {
         setText(disc.getName());
      }
   }

}
