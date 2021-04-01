package com.axokoi.bandurriaj.services.tagging;

import com.axokoi.bandurriaj.model.Disc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * For the moment this class is just a pass through for the only ProviderFacade we have, which is the MusicBrainzFacade
 */
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

	//todo How will we handle the case when there will be more than one provider?
	public Optional<Disc> getDiscFromUniqueIdentifier(String identifier){
		log.info("Initiating tagging from unique identifier:" + identifier);
		return taggingProviders.get(0).getFullDiscInfoFromUniqueIdentifier(identifier);
	}

	public List<Disc> lookupFromDiscId(String discId){
		log.info("starting lookup from discid:" + discId);
		return taggingProviders.get(0).lookUpFromDiscId(discId);
	}
}
