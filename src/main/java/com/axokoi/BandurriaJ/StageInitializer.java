package com.axokoi.BandurriaJ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.DiscRepository;
import com.axokoi.BandurriaJ.views.ArtistView;
import com.axokoi.BandurriaJ.views.CatalogueView;
import com.axokoi.BandurriaJ.views.DiscView;
import com.axokoi.BandurriaJ.views.SmartSearchView;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<MainApplication.StageReadyEvent> {

	@Autowired
	private DiscRepository discRepository;

	@Autowired
	private CatalogueView catalogueView;

	@Autowired
	private DiscView discView;

	@Autowired
	ArtistView artistView;

	@Autowired
	private SmartSearchView smartSearchView;

	@Autowired
	private DBCreation dbCreation;

	@Override
	public void onApplicationEvent(MainApplication.StageReadyEvent event) {

		dbCreation.init();
		catalogueView.refresh();

		Stage stage = event.getStage();
		BorderPane mainPane = new BorderPane();

		MenuBar menuBar = getMenuBar();

		VBox center = new VBox(discView,artistView);
		mainPane.setTop(menuBar);
		mainPane.setLeft(catalogueView);
		mainPane.setCenter(center);
		mainPane.setRight(smartSearchView);

		HBox footerView = new HBox();
		footerView.getChildren().add(new Text("BandurriaJ by Axokoi"));
		mainPane.setBottom(footerView);

		stage.setTitle("BandurriaJ");
		stage.setScene(new Scene(mainPane, 1000, 350));
		stage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		stage.sizeToScene();
		stage.show();

	}

	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("File");
		MenuItem menuItem1 = new MenuItem("save");
		menu1.getItems().add(menuItem1);

		Menu menu2 = new Menu("Import");
		MenuItem menu2Item1 = new MenuItem("from CD");
		menu2.getItems().add(menu2Item1);

		menuBar.getMenus().add(menu1);
		menuBar.getMenus().add(menu2);
		return menuBar;
	}

}
