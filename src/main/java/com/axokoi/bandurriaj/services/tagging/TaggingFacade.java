package com.axokoi.bandurriaj.services.tagging;

import java.util.List;

import com.axokoi.bandurriaj.model.Disc;

public class TaggingFacade {

	List<ProviderFacade> taggingProviders;

	public Disc getDisc(String discID) {
		//this will change some day, as we plan to fetch info from different providers.
		//It will need to be get well written in the future.
		return taggingProviders.get(0).getDiscInfo(discID);
	}
}
