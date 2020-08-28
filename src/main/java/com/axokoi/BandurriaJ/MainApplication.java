package com.axokoi.BandurriaJ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.axokoi.BandurriaJ.model.DiscRepository;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

@EnableJpaRepositories("application.model")
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
