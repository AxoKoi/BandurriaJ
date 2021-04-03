package com.axokoi.bandurriaj.views;

import java.util.List;

import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.scene.layout.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.controllers.SmartSearchController;
import com.axokoi.bandurriaj.model.Searchable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Component
public final class SmartSearchView extends VBox {

	@Autowired
	private SmartSearchController smartSearchController;

	@Autowired
	private final MessagesProvider messagesProvider;

	private final Label search ;
	private final TextField inputSearch = new TextField();
	private final ListView<Searchable> results = new ListView<>();

	public SmartSearchView(MessagesProvider messagesProvider) {
		this.messagesProvider = messagesProvider;
		search = new Label(this.messagesProvider.getMessageFrom("smartsearch.view.search"));
		inputSearch.setOnAction(x -> smartSearchController.smartSearch(inputSearch.getText().trim()));
		getChildren().addAll(search, inputSearch, results);

		VBox.setVgrow(results, Priority.ALWAYS);

		this.setPadding(new Insets(14));
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

		this.results.setOnMouseClicked(event -> smartSearchController.dispatchRefreshToController(this.results.getSelectionModel().getSelectedItem()));

	}

	public static class SearchableCell extends ListCell<Searchable> {
		@Override
		protected void updateItem(Searchable searchable, boolean empty) {
			super.updateItem(searchable, empty);
			setText(searchable == null ? "" : searchable.getName() );
		}
	}
}
