package com.axokoi.BandurriaJ.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.Controllers.CatalogueController;
import com.axokoi.BandurriaJ.Controllers.DiscController;
import com.axokoi.BandurriaJ.model.Catalogue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class CatalogueView extends VBox {

	@Autowired
	DiscController discController;

	private final CatalogueController catalogueController;

	public CatalogueView(CatalogueController catalogueController) {
		this.catalogueController = catalogueController;
		this.setPadding(new Insets(10));
		this.setSpacing(8);
	}

	public void refresh() {
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

		treeView.addEventHandler(KeyEvent.KEY_PRESSED, getCatalogueKeyEventEventHandler(treeView));
		treeView.addEventHandler(MouseEvent.MOUSE_CLICKED,
				event -> {
					if (treeView.getSelectionModel().getSelectedItem() != null) {
						discController.refreshViewWithName(treeView.getSelectionModel().getSelectedItem().getValue());
					}
				});
		treeView.setRoot(rootItem);
		addContextMenu(treeView);
		rootItem.setExpanded(true);
		return treeView;
	}

	private EventHandler<KeyEvent> getCatalogueKeyEventEventHandler(TreeView<String> treeView) {
		return event -> {
			if (!(event.getSource() instanceof TreeView)) {
				return;
			}
			boolean isCD = ((TreeView<String>) event.getSource()).getSelectionModel().getSelectedItem().isLeaf();

			if (isCD) {
				switch (event.getCode()) {
				case ENTER:
				case UP:
				case DOWN:
					discController.refreshViewWithName(
							treeView.getSelectionModel().getSelectedItems().get(0).getValue());
					break;
				default:
					break;
				}
			} else {
				switch (event.getCode()) {
				case DELETE:
					displayDeleteCatalogue(treeView.getSelectionModel().getSelectedItems().get(0).getValue());
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

		HBox saveCancelBox = new HBox(deleteButton, cancelButton);
		VBox vbox = new VBox(warning, saveCancelBox);

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
//todo implement this when we fix this view
	}
}
