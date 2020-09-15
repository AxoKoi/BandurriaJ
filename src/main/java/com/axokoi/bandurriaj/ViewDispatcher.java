package com.axokoi.bandurriaj;

import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

@Component
public class ViewDispatcher {
	private  BorderPane borderPane;

	public  void setBorderPane(BorderPane borderPane) {
		this.borderPane = borderPane;
	}

	public void replaceCenterWith(Node node) {
		borderPane.setCenter(node);
	}
}
