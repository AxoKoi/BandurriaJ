package com.axokoi.BandurriaJ.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.Controllers.SmartSearchController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Component
public class SmartSearchView  extends VBox {

	@Autowired
	SmartSearchController smartSearchController;

	private Label search = new Label("Enter your search");
	private TextField inputSearch = new TextField();
	private ListView<String> results = new ListView<>();

	public SmartSearchView() {
		inputSearch.setOnAction(x-> smartSearchController.smartSearch(inputSearch.getText().trim()));
		this.getChildren().addAll(search, inputSearch, results);
	}


	public void refresh(List<String> results){
		ObservableList<String> items = FXCollections.observableArrayList(results);
		this.results.getItems().clear();
		this.results.getItems().addAll(items);
	}


}
