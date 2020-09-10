package com.axokoi.BandurriaJ.services.tagging;

import java.util.List;

import com.axokoi.BandurriaJ.model.Disc;

public class TaggingFacade {

	List<ProviderFacade> taggingProviders;

	public Disc getDisc(String discID) {
		//this will change some day, as we plan to fetch info from different providers.
		//It will need to be get well written in the future.
		return taggingProviders.get(0).getDiscInfo(discID);
	}
}
