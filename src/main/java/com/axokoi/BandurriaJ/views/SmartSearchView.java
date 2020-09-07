package com.axokoi.BandurriaJ.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.Controllers.ArtistController;
import com.axokoi.BandurriaJ.Controllers.DiscController;
import com.axokoi.BandurriaJ.Controllers.SmartSearchController;
import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.model.Searchable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Component
public class SmartSearchView extends VBox {

	@Autowired
	SmartSearchController smartSearchController;

	@Autowired
	DiscController discController;
	@Autowired
	ArtistController artistController;

/*
	@Autowired
	BandController bandController;
*/

	private final Label search = new Label("Enter your search");
	private final TextField inputSearch = new TextField();
	private final ListView<Searchable> results = new ListView<>();

	public SmartSearchView() {
		inputSearch.setOnAction(x -> smartSearchController.smartSearch(inputSearch.getText().trim()));
		getChildren().addAll(search, inputSearch, results);
		this.setPadding(new Insets(10));
		this.setSpacing(8);

	}

	public void refresh(List<Searchable> smartSearchResult) {
		ObservableList<Searchable> items = FXCollections.observableArrayList(smartSearchResult);

		this.results.getItems().clear();
		if (items.isEmpty()) {
			return;
		}

		this.results.getItems().addAll(items);
		this.results.setCellFactory(list -> new SearchableCell());

		this.results.setOnMouseClicked(event -> {
			//needs to handle all possible results: Artist, CD, catalogues, bands.
			dispatchRefreshToController();
		});

	}

	private void dispatchRefreshToController() {

		Searchable selectedItem = this.results.getSelectionModel().getSelectedItem();
		if (selectedItem instanceof Disc) {
			discController.refreshView((Disc) selectedItem);
		} else if (selectedItem instanceof Artist) {
			artistController.refreshView((Artist) selectedItem);
		}

	}

	static class SearchableCell extends ListCell<Searchable> {
		@Override
		protected void updateItem(Searchable searchable, boolean empty) {
			super.updateItem(searchable, empty);

			setText(searchable == null ? "" : searchable.getName());
		}
	}
}
