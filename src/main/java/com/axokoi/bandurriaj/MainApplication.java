package com.axokoi.bandurriaj;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class MainApplication extends Application {

	public static class StageReadyEvent extends ApplicationEvent {
		public StageReadyEvent(Stage stage) {
			super(stage);
		}

		public Stage getStage() {
			return (Stage) getSource();
		}
	}

	public static class FXApplicationClosedEvent extends  ApplicationEvent{

		public FXApplicationClosedEvent(Object source) {
			super(source);
		}
		//IRO Do we need the Stage?

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
      applicationContext.publishEvent(new FXApplicationClosedEvent(""));
		applicationContext.close();
		Platform.exit();
	}


	@Override
	public void start(Stage stage) {
		applicationContext.publishEvent(new StageReadyEvent(stage));
	}

}
