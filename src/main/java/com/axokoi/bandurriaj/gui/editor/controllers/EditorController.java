package com.axokoi.bandurriaj.gui.editor.controllers;

import com.axokoi.bandurriaj.gui.commons.GuiController;
import com.axokoi.bandurriaj.model.Searchable;
import javafx.event.ActionEvent;


public abstract class EditorController<S extends Searchable> extends GuiController<S> {

   public abstract void onCancel(ActionEvent event);

   public abstract void onSave(ActionEvent event);
}
