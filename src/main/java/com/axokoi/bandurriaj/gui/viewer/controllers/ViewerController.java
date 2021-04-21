package com.axokoi.bandurriaj.gui.viewer.controllers;

import com.axokoi.bandurriaj.ViewDispatcher;
import com.axokoi.bandurriaj.gui.commons.GuiController;
import com.axokoi.bandurriaj.model.Searchable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class ViewerController<S extends Searchable> extends GuiController<S> {
	@Autowired
	ViewDispatcher viewDispatcher;

	/**
	 * A method that allow a controller to update it's view with the given Searchable and to replace the center pannel with it's view
	 * Every controller exposes this method so it can called by other controllers.
	 * @param searchable if it's null, no dispatching is done.
	 */
	public void displayViewCenter(S searchable) {
		if(searchable == null){
			return;
		}
		this.refreshView(searchable);
		viewDispatcher.replaceCenterWith(this.getView());
	}


}
