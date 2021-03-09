package com.axokoi.bandurriaj;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
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

	}

	private ConfigurableApplicationContext applicationContext;

	@Override
	public void init() {
		applicationContext = new SpringApplicationBuilder(SpringBootMain.class).run();
	}

	@Override
	public void stop() {
      log.info("Stopping application. Publishing event.");
		applicationContext.publishEvent(new FXApplicationClosedEvent(""));
		applicationContext.close();
		Platform.exit();
	}


	@Override
	public void start(Stage stage) {
		applicationContext.publishEvent(new StageReadyEvent(stage));
	}

}
