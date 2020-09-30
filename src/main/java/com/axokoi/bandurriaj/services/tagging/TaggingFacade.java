package com.axokoi.bandurriaj.services.tagging;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.axokoi.bandurriaj.model.Disc;

public class TaggingFacade {

	@Autowired
	List<ProviderFacade> taggingProviders;

	public List<Disc> getDisc(String discID) {
		//this will change some day, as we plan to fetch info from different providers.
		//It will need to be get well written in the future.
		return taggingProviders.get(0).getDiscInfoFromDiscId(discID);
	}
}
