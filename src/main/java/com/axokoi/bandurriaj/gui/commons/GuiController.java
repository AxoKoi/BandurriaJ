package com.axokoi.bandurriaj.gui.commons;

import com.axokoi.bandurriaj.model.Searchable;
import javafx.scene.Node;

public abstract class GuiController<S extends Searchable> {

   /**
    * @return A node which correspond to the respective view.
    */
   protected abstract Node getView();

   /**
    * A method to refresh the view with the given model
    * @param searchable The searchable model
    */
   protected abstract void refreshView(S searchable);
}
