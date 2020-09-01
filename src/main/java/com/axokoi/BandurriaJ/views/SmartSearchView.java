package com.axokoi.BandurriaJ.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.Controllers.DiscController;
import com.axokoi.BandurriaJ.Controllers.SmartSearchController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Component
public class SmartSearchView extends VBox {

	@Autowired
	SmartSearchController smartSearchController;

	@Autowired
	DiscController discController;

	private final Label search = new Label("Enter your search");
	private final TextField inputSearch = new TextField();
	private final ListView<String> results = new ListView<>();

	public SmartSearchView() {
		inputSearch.setOnAction(x -> smartSearchController.smartSearch(inputSearch.getText().trim()));
		getChildren().addAll(search, inputSearch, results);
		this.setPadding(new Insets(10));
		this.setSpacing(8);

	}

	public void refresh(List<String> results) {
		ObservableList<String> items = FXCollections.observableArrayList(results);

		this.results.getItems().clear();
		this.results.getItems().addAll(items);
		this.results.setOnMouseClicked(event -> {
			//needs to handle all possible results: Artist, CD, catalogues, bands.
			discController.refreshView(this.results.getSelectionModel().getSelectedItem());
		});

	}

}
