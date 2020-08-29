package com.axokoi.BandurriaJ.views;

import java.util.List;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.Controllers.CatalogueController;
import com.axokoi.BandurriaJ.model.Catalogue;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

@Component
public class CatalogueView extends VBox {

	private final CatalogueController catalogueController;

	public CatalogueView(CatalogueController catalogueController) {
		this.catalogueController = catalogueController;
	}

	public void update() {
		TreeView<String> treeView = cataloguesToTreeView();

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
		treeView.setRoot(rootItem);
		return treeView;
	}

}
