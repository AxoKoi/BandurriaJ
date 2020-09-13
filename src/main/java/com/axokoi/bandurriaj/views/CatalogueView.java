package com.axokoi.bandurriaj.views;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.controllers.CatalogueController;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Searchable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
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
	private CatalogueController catalogueController;

	private TreeView<Searchable> treeView = new TreeView<>();

	public CatalogueView() {

		this.setPadding(new Insets(14));
		this.setSpacing(8);
		this.getStyleClass().add("left-pane");
	}

	public void refresh() {
		//todo doing this for every refresh, every disc may be too heavy. To check
		treeView = cataloguesToTreeView();
		treeView.setCellFactory(x -> new SearchableCell());
		this.getChildren().clear();
		this.getChildren().add(treeView);
	}

	private TreeView<Searchable> cataloguesToTreeView() {
		TreeView<Searchable> treeViewToBuild = new TreeView<>();
		TreeItem<Searchable> rootItem = new TreeItem<>(() -> "Catalogues");
		List<Catalogue> catalogues = catalogueController.getCatalogues();
		catalogues.forEach(catalogue -> {
					TreeItem<Searchable> catalogueItem = new TreeItem<>(catalogue);
					catalogue.getDiscs().forEach(disc -> catalogueItem.getChildren().add(new TreeItem<>(disc)));
					rootItem.getChildren().add(catalogueItem);
				}
		);

		treeViewToBuild.addEventHandler(KeyEvent.KEY_PRESSED, getCatalogueKeyEventEventHandler(treeViewToBuild));
		treeViewToBuild.addEventHandler(MouseEvent.MOUSE_CLICKED,
				event -> {
					if (treeViewToBuild.getSelectionModel().getSelectedItem() != null &&
							treeViewToBuild.getSelectionModel().getSelectedItem().getValue() instanceof Disc) {
						catalogueController.dispatchRefreshToController((Disc) treeViewToBuild.getSelectionModel().getSelectedItem().getValue());
					}

				});
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
				switch (event.getCode()) {
				case ENTER:
				case UP:
				case DOWN:
					catalogueController.dispatchRefreshToController((Disc) treeViewToBuild.getSelectionModel().getSelectedItem().getValue());
					break;
				default:
					break;
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
		MenuItem addNewCatalogueItem = new MenuItem("add a new Catalogue");
		addNewCatalogueItem.setOnAction(displayAddNewCataloguePopupHandler());

		catalogueContextMenu.getItems().addAll(addNewCatalogueItem);

		treeViewToBuild.setContextMenu(catalogueContextMenu);
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
		treeView.getRoot().getChildren().stream()
				.filter(x -> x.getValue().getName().equals(catalogue.getName())).findFirst()
				.ifPresent(searchableTreeItem -> treeView.getSelectionModel().select(treeView.getRow(searchableTreeItem)));

	}

	static class SearchableCell extends TreeCell<Searchable> {
		@Override
		protected void updateItem(Searchable searchable, boolean empty) {
			super.updateItem(searchable, empty);

			setText(searchable == null ? "" : searchable.getName());
		}
	}
}
