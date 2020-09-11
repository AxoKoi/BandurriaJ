package com.axokoi.bandurriaj;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class MainApplication extends Application {

	public static class StageReadyEvent extends ApplicationEvent {
		public StageReadyEvent(Stage stage) {
			super(stage);
		}

		public Stage getStage() {
			return (Stage) getSource();
		}
	}

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

}
