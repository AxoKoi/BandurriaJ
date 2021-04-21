package com.axokoi.bandurriaj.gui.commons.cells.list;

import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.*;
import javafx.scene.control.ListCell;

public class SearchableCell extends ListCell<Searchable> {


   private final MessagesProvider messagesProvider;

   public SearchableCell(MessagesProvider messagesProvider) {
      this.messagesProvider = messagesProvider;
   }

   @Override
   protected void updateItem(Searchable searchable, boolean empty) {
      super.updateItem(searchable, empty);
      if (searchable == null) {
         setText("");
         return;
      }

      String type = "";
      if (searchable instanceof Artist) {
         type = messagesProvider.getMessageFrom("searchable.cell.artist");
      } else if (searchable instanceof Disc) {
         type = messagesProvider.getMessageFrom("searchable.cell.disc");
      } else if (searchable instanceof Track) {
         type = messagesProvider.getMessageFrom("searchable.cell.track");
      } else if (searchable instanceof Catalogue) {
         type = messagesProvider.getMessageFrom("searchable.cell.catalogue");
      }
      else{
         type = messagesProvider.getMessageFrom("searchable.cell.unknown");
      }

      setText(searchable.getName() + " " + type);
   }

}
