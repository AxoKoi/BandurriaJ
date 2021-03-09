package com.axokoi.bandurriaj.services.tagging;

import com.axokoi.bandurriaj.model.Disc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class TaggingFacade {

	private final List<ProviderFacade> taggingProviders;

	public TaggingFacade(List<ProviderFacade> taggingProviders) {
		this.taggingProviders = taggingProviders;
	}

	public List<Disc> getDisc(String discID) {
		//this will change some day, as we plan to fetch info from different providers.
		//It will need to be get well written in the future.
		log.info("Initiating tagging of discId:" + discID);
		return taggingProviders.get(0).getDiscInfoFromDiscId(discID);
	}
}
