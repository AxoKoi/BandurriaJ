package com.axokoi.bandurriaj.gui.commons.cells.list;

import com.axokoi.bandurriaj.model.Artist;
import javafx.scene.control.ListCell;

public class ArtistCell extends ListCell<Artist> {

   @Override
   protected void updateItem(Artist artist, boolean empty) {
      super.updateItem(artist, empty);
      if (artist == null) {
         setText("");
         disabledProperty();
      } else {
         setText(artist.getName());
      }

   }
}

