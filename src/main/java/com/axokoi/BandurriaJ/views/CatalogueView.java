package com.axokoi.BandurriaJ.views;

import java.util.List;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.Controllers.CatalogueController;
import com.axokoi.BandurriaJ.model.Catalogue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CatalogueView extends VBox {

	private final CatalogueController catalogueController;

	public CatalogueView(CatalogueController catalogueController) {
		this.catalogueController = catalogueController;
	}

	public void update() {
		TreeView<String> treeView = cataloguesToTreeView();
		this.getChildren().clear();
		this.getChildren().add(treeView);

	}

	private TreeView<String> cataloguesToTreeView() {
		TreeView<String> treeView = new TreeView<>();
		TreeItem<String> rootItem = new TreeItem<>("Catalogues");
		List<Catalogue> catalogues = catalogueController.getCatalogues();
		catalogues.forEach(catalogue -> {
					TreeItem<String> catalogueItem = new TreeItem<>(catalogue.getName());
					catalogue.getDiscs().forEach(disc -> catalogueItem.getChildren().add(new TreeItem<>(disc.getName())));
					rootItem.getChildren().add(catalogueItem);
				}
		);
		treeView.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.DELETE) {
				displayDeleteCatalogue(treeView.getSelectionModel().getSelectedItems().get(0).getValue());
			}
			if (event.getCode() == KeyCode.INSERT) {
				displayAddNewCataloguePopUp();
			}
		});
		treeView.setRoot(rootItem);
		addContextMenu(treeView);
		rootItem.setExpanded(true);
		return treeView;
	}

	private void addContextMenu(TreeView<String> treeView) {
		ContextMenu catalogueContextMenu = new ContextMenu();
		MenuItem addNewCatalogueItem = new MenuItem("add a new Catalogue");
		addNewCatalogueItem.setOnAction(displayAddNewCataloguePopupHandler());

		catalogueContextMenu.getItems().addAll(addNewCatalogueItem);

		treeView.setContextMenu(catalogueContextMenu);

	}

	private EventHandler<ActionEvent> displayAddNewCataloguePopupHandler() {
		return event -> displayAddNewCataloguePopUp();
	}

	private void displayDeleteCatalogue(String catalogueName) {
		Stage popUpStage = new Stage();
		Label warning = new Label("Warning! This will delete your catalogue: " + catalogueName);
		Button deleteButton = new Button("Delete");
		deleteButton.setOnMouseClicked(e -> {
			catalogueController.deleteCatalogue(catalogueName);
			((Stage) deleteButton.getScene().getWindow()).close();
		});
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnMouseClicked(e -> ((Stage) cancelButton.getScene().getWindow()).close());
		VBox vbox = new VBox(warning, deleteButton, cancelButton);
		Scene popUpScene = new Scene(vbox, 300, 100);
		popUpStage.setScene(popUpScene);
		popUpStage.show();
	}

	private void displayAddNewCataloguePopUp() {

		Stage popUpStage = new Stage();

		Label catalogueName = new Label("Catalogue name");
		TextField catalogueNameInput = new TextField("Enter the name here");
		Button saveButton = new Button("Save");
		saveButton.setOnMouseClicked(e -> {
			catalogueController.addNewCatalogue(catalogueNameInput.getText());
			((Stage) saveButton.getScene().getWindow()).close();
		});
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnMouseClicked(e -> ((Stage) cancelButton.getScene().getWindow()).close());

		VBox vbox = new VBox(catalogueName, catalogueNameInput, saveButton, cancelButton);
		Scene popUpScene = new Scene(vbox, 200, 200);

		popUpStage.setScene(popUpScene);
		popUpScene.addEventHandler(KeyEvent.KEY_PRESSED,event->{
			if(event.getCode()==KeyCode.ENTER){
				catalogueController.addNewCatalogue(catalogueNameInput.getText());
				popUpStage.close();
			}
		});
		popUpStage.show();
	}

}
