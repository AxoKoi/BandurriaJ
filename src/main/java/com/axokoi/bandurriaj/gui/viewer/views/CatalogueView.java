package com.axokoi.bandurriaj.gui.viewer.views;


import com.axokoi.bandurriaj.gui.commons.cells.tree.SearchableCell;
import com.axokoi.bandurriaj.gui.commons.handlers.mouse.SinglePrimaryClickHandler;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.gui.viewer.controllers.CatalogueController;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Searchable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class CatalogueView extends VBox {

	@Autowired
	private  CatalogueController catalogueController;
	@Autowired
	private MessagesProvider messagesProvider;

	private TreeView<Searchable> treeView = new TreeView<>();

	public CatalogueView() {

		this.setPadding(new Insets(14));
		this.setSpacing(8);

		this.prefHeight(Double.MAX_VALUE);
		VBox.setVgrow(treeView, Priority.ALWAYS);
		this.getChildren().add(treeView);
	}

	public void refresh() {
		//todo doing this for every refresh, every disc may be too heavy. To check
		treeView = cataloguesToTreeView();
		treeView.setCellFactory(x -> new SearchableCell());
		VBox.setVgrow(treeView, Priority.ALWAYS);
		this.getChildren().clear();
		this.getChildren().add(treeView);
	}

	private TreeView<Searchable> cataloguesToTreeView() {
		TreeView<Searchable> treeViewToBuild = new TreeView<>();
		TreeItem<Searchable> rootItem = new TreeItem<>(() -> messagesProvider.getMessageFrom("catalogue.view.root.label"));
		List<Catalogue> catalogues = catalogueController.getCatalogues();
		catalogues.forEach(catalogue -> {
					TreeItem<Searchable> catalogueItem = new TreeItem<>(catalogue);
					catalogue.getDiscs().forEach(disc -> catalogueItem.getChildren().add(new TreeItem<>(disc)));
					rootItem.getChildren().add(catalogueItem);
				}
		);

		treeViewToBuild.addEventHandler(KeyEvent.KEY_PRESSED, getCatalogueKeyEventEventHandler(treeViewToBuild));
		treeViewToBuild.setOnMouseClicked(new SinglePrimaryClickHandler(mouseEvent->	{ if (treeViewToBuild.getSelectionModel().getSelectedItem() != null &&
				treeViewToBuild.getSelectionModel().getSelectedItem().getValue() instanceof Disc) {
			catalogueController.dispatchRefreshToController((Disc) treeViewToBuild.getSelectionModel().getSelectedItem().getValue());
		}}));

		treeViewToBuild.setRoot(rootItem);
		addContextMenu(treeViewToBuild);
		rootItem.setExpanded(true);
		return treeViewToBuild;
	}

	private EventHandler<KeyEvent> getCatalogueKeyEventEventHandler(TreeView<Searchable> treeViewToBuild) {
		return event -> {
			if (!(event.getSource() instanceof TreeView)) {
				return;
			}
			boolean isCD = ((TreeView<Searchable>) event.getSource()).getSelectionModel().getSelectedItem().getValue() instanceof Disc;
			boolean isCatalogue = ((TreeView<Searchable>) event.getSource()).getSelectionModel().getSelectedItem().getValue() instanceof Catalogue;

			if (isCD) {
				if (event.getCode() == KeyCode.ENTER) {
					catalogueController.dispatchRefreshToController((Disc) treeViewToBuild.getSelectionModel().getSelectedItem().getValue());
				}
			} else if (isCatalogue) {
				switch (event.getCode()) {
				case DELETE:
					displayDeleteCatalogue(treeViewToBuild.getSelectionModel().getSelectedItem().getValue().getName());
					break;
				case INSERT:
					displayAddNewCataloguePopUp();
					break;
				default:
					break;
				}
			}
		};
	}

	private void addContextMenu(TreeView<Searchable> treeViewToBuild) {
		ContextMenu catalogueContextMenu = new ContextMenu();
		MenuItem addNewCatalogueItem = new MenuItem(messagesProvider.getMessageFrom("catalogue.view.add.new.catalogue.item"));
		addNewCatalogueItem.setOnAction(displayAddNewCataloguePopupHandler());

		MenuItem editCatalogue = new MenuItem(messagesProvider.getMessageFrom("catalogue.view.edit.catalogue.item"));
		editCatalogue.setOnAction(event->catalogueController.displayEditMenu(event, (Catalogue) treeView.getSelectionModel().getSelectedItem().getValue()));
		catalogueContextMenu.getItems().addAll(addNewCatalogueItem,editCatalogue);

		treeViewToBuild.setContextMenu(catalogueContextMenu);
	}

	private EventHandler<ActionEvent> displayAddNewCataloguePopupHandler() {
		return event -> displayAddNewCataloguePopUp();
	}

	private void displayDeleteCatalogue(String catalogueName) {
		Stage popUpStage = new Stage();
		Label warning = new Label(messagesProvider.getMessageFrom("catalogue.view.delete.catalogue",catalogueName));
		Button deleteButton = new Button(messagesProvider.getMessageFrom("button.delete"));
		deleteButton.setOnMouseClicked(e -> {
			catalogueController.deleteCatalogue(catalogueName);
			((Stage) deleteButton.getScene().getWindow()).close();
		});
		Button cancelButton = new Button(messagesProvider.getMessageFrom("button.cancel"));
		cancelButton.setOnMouseClicked(e -> ((Stage) cancelButton.getScene().getWindow()).close());

		HBox saveCancelBox = new HBox(deleteButton, cancelButton);
		VBox vbox = new VBox(warning, saveCancelBox);

		Scene popUpScene = new Scene(vbox, 300, 100);
		popUpStage.setScene(popUpScene);
		popUpStage.show();
	}

	private void displayAddNewCataloguePopUp() {

		Stage popUpStage = new Stage();

		Label catalogueName = new Label(messagesProvider.getMessageFrom("catalogue.view.add.new.popup.catalogue.name"));
		TextField catalogueNameInput = new TextField(messagesProvider.getMessageFrom("catalogue.view.add.new.popup.catalogue.name.input.text"));
		Button saveButton = new Button(messagesProvider.getMessageFrom("button.save"));
		saveButton.setOnMouseClicked(e -> {
			catalogueController.addNewCatalogue(catalogueNameInput.getText());
			((Stage) saveButton.getScene().getWindow()).close();
		});
		Button cancelButton = new Button(messagesProvider.getMessageFrom("button.cancel"));
		cancelButton.setOnMouseClicked(e -> ((Stage) cancelButton.getScene().getWindow()).close());

		HBox catalogueHBox = new HBox(catalogueName, catalogueNameInput);
		HBox saveCancelButton = new HBox(saveButton, cancelButton);
		VBox vbox = new VBox(catalogueHBox, saveCancelButton);
		Scene popUpScene = new Scene(vbox, 300, 100);

		popUpStage.setScene(popUpScene);
		popUpScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.ENTER) {
				catalogueController.addNewCatalogue(catalogueNameInput.getText());
				popUpStage.close();
			}
		});
		popUpStage.show();
	}

	public void focus(Catalogue catalogue) {
		treeView.getRoot().getChildren().stream()
				.filter(x -> x.getValue().getName().equals(catalogue.getName())).findFirst()
				.ifPresent(searchableTreeItem -> treeView.getSelectionModel().select(treeView.getRow(searchableTreeItem)));

	}
}
