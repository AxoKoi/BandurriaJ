package com.axokoi.BandurriaJ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.DiscRepository;
import com.axokoi.BandurriaJ.views.CatalogueView;
import com.axokoi.BandurriaJ.views.DiscView;
import com.axokoi.BandurriaJ.views.SmartSearchView;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	private SmartSearchView smartSearchView;

	@Autowired
	private DBCreation dbCreation;

	@Override
	public void onApplicationEvent(MainApplication.StageReadyEvent event) {
		Stage stage = event.getStage();
		dbCreation.init();
		MenuBar menuBar = getMenuBar();
		BorderPane mainPane = new BorderPane(menuBar);

		stage.setTitle("BandurriaJ");

/*
		FileChooser fileChooser = new FileChooser();
Button button = new Button("Select File");

		button.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(stage);
		});*/

		/*vboxLeft.getChildren().add(button);*/

		catalogueView.refresh();

		mainPane.setLeft(catalogueView);
		mainPane.setCenter(discView);
		mainPane.setRight(smartSearchView);
		HBox footerView = new HBox();
		footerView.getChildren().add(new Text("BandurriaJ by Axokoi"));
		mainPane.setBottom(footerView);
		mainPane.setTop(menuBar);
		stage.setScene(new Scene(mainPane, 1000, 350));
		stage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
		catalogueView.prefHeightProperty().bind(stage.getScene().heightProperty().multiply(0.8));
		discView.prefHeightProperty().bind(stage.getScene().heightProperty().multiply(0.8));
		smartSearchView.prefHeightProperty().bind(stage.getScene().heightProperty().multiply(0.8));
		stage.show();

	}

	private MenuBar getMenuBar() {
		MenuBar menuBar = new MenuBar();
		Menu menu1 = new Menu("menu1");
		MenuItem menuItem1 = new MenuItem("Item1");
		MenuItem menuItem2 = new MenuItem("Item2");
		menu1.getItems().add(menuItem1);
		menu1.getItems().add(menuItem2);
		menuBar.getMenus().add(menu1);
		return menuBar;
	}

}
