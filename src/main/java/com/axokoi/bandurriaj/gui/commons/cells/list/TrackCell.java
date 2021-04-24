package com.axokoi.bandurriaj.gui.commons.cells.list;

import com.axokoi.bandurriaj.model.Track;
import javafx.scene.control.ListCell;

public class TrackCell extends ListCell<Track> {
   @Override
   protected void updateItem(Track track, boolean empty) {
      super.updateItem(track, empty);
      if (track == null) {
         disableProperty();
         setText("");
      } else {
         setText(track.getNumber() + " - " + track.getName() + " - " + track.getDuration());
      }
   }

}
