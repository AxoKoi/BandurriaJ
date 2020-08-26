package application;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import application.model.Disc;
import application.model.DiscRepository;
import application.views.DiscView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainApplication extends Application {

	static class StageReadyEvent extends ApplicationEvent {
		public StageReadyEvent(Stage stage) {
			super(stage);
		}

		public Stage getStage() {
			return (Stage) getSource();
		}
	}

	@Autowired
	DiscRepository discRepository;

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() {
		applicationContext = new SpringApplicationBuilder(SpringBootMain.class).run();
	}

	@Override
	public void stop() {
		applicationContext.close();
		Platform.exit();
	}

	@Override
	public void start(Stage stage) {

		applicationContext.publishEvent(new StageReadyEvent(stage));
	}
/*
	public static void main(String[] args) {
		launch(args);
	}
*/
}
