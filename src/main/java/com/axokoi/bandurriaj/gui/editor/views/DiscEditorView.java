package com.axokoi.bandurriaj.gui.editor.views;

import com.axokoi.bandurriaj.gui.editor.controllers.EditorController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Disc;

public class DiscEditorView extends EditorView<Disc> {


   protected DiscEditorView(EditorController<Disc> controller, MessagesProvider messagesProvider) {
      super(controller, messagesProvider);
   }

   @Override
   public EditorView<Disc> refresh(Disc disc) {
      entityToEdit = disc;
      return this;
   }
}
