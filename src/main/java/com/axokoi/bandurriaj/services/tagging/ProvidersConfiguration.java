package com.axokoi.bandurriaj.services.tagging;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.axokoi.bandurriaj.services.tagging.musicbrainz.MusicBrainzFacade;

//todo do we need this finally?
@Configuration
public class ProvidersConfiguration {
	@Bean
	public TaggingFacade getTaggingFacade() {
		return new TaggingFacade();
	}

	@Bean
	List<ProviderFacade> taggingProviders() {
		return Arrays.asList(new MusicBrainzFacade());
	}
}
