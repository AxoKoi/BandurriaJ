package com.axokoi.bandurriaj.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.ViewDispatcher;
import com.axokoi.bandurriaj.model.Searchable;

import javafx.scene.Node;

@Component
public abstract class GuiController<S extends Searchable> {
	@Autowired
	ViewDispatcher viewDispatcher;

	public void displayViewCenter(S searchable) {

		this.refreshView(searchable);
		viewDispatcher.replaceCenterWith(this.getView());
	}

	abstract Node getView();

	abstract void refreshView(S searchable);

}
