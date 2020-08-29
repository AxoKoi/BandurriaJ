package com.axokoi.BandurriaJ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.DiscRepository;
import com.axokoi.BandurriaJ.views.CatalogueView;
import com.axokoi.BandurriaJ.views.DiscView;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class StageInitializer implements ApplicationListener<MainApplication.StageReadyEvent> {

	@Autowired
	private DiscRepository discRepository;

	@Autowired
	private CatalogueView catalogueView;

	@Autowired
	private DBCreation dbCreation;

	@Override
	public void onApplicationEvent(MainApplication.StageReadyEvent event) {
		Stage stage = event.getStage();
		dbCreation.init();
		MenuBar menuBar = getMenuBar();
		VBox mainBox = new VBox(menuBar);

		stage.setTitle("BandurriaJ");

/*
		FileChooser fileChooser = new FileChooser();

		Button button = new Button("Select File");
		button.setOnAction(e -> {
			File selectedFile = fileChooser.showOpenDialog(stage);
		});*/

		/*vboxLeft.getChildren().add(button);*/

		catalogueView.update();
		VBox vboxRight = new DiscView(discRepository.findAll().iterator().next());
		HBox hbox = new HBox(catalogueView, vboxRight);
		mainBox.getChildren().add(hbox);
		hbox.setPrefWidth(300);

		stage.setScene(new Scene(mainBox, 800, 500));
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
