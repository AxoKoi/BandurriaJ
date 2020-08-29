package com.axokoi.BandurriaJ;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.axokoi.BandurriaJ.Controllers.CatalogueController;
import com.axokoi.BandurriaJ.views.CatalogueView;

@org.springframework.context.annotation.Configuration
public class Configuration {

/*	@Bean
	public DBCreation dBCreation() {
		return new DBCreation();
	}

	@Bean
	@DependsOn("dBCreation")
	public CatalogueView catalogueView(CatalogueController catalogueController) {
		return new CatalogueView(catalogueController);
	}

	@Bean
	@DependsOn("dBCreation")
	public StageInitializer stageInitializer() {
		return new StageInitializer();
	}*/
}
