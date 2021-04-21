package com.axokoi.bandurriaj.gui.viewer.views;

import com.axokoi.bandurriaj.gui.commons.cells.list.SearchableCell;
import com.axokoi.bandurriaj.gui.commons.handlers.mouse.DoubleClickHandler;
import com.axokoi.bandurriaj.gui.viewer.controllers.SmartSearchController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Searchable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
		this.results.setCellFactory(list -> new SearchableCell(messagesProvider));

		this.results.setOnMouseClicked(new DoubleClickHandler(x->smartSearchController.dispatchRefreshToController(this.results.getSelectionModel().getSelectedItem())));

	}


}
