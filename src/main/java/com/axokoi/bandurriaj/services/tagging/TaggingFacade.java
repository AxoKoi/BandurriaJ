package com.axokoi.bandurriaj.services.tagging;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.ExternalIdentifier;
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


	/**
	 * The entry point for retrieving a disc with all the related information from an external tagging provider.
	 * Currently, only Musicbrainz is used to build the disc, but this may change in the future. At that moment, the facade will
	 * be extended.
	 * @param identifier The ExternalIdentifier Entity corresponding to the disc.
	 * @return An optional Disc which will be empty if the disc couldn't been found.
	 */
	public Optional<Disc> getDiscFromUniqueIdentifier(ExternalIdentifier identifier){
		log.info("Initiating tagging from unique identifier " + identifier.getType() + ":" + identifier.getIdentifier());
		return taggingProviders.get(0).getFullDiscInfoFromUniqueIdentifier(identifier.getIdentifier());
	}

	/**
	 * A lookup method from the discID of a CD, as computed by libDiscId. Currently, the lookup is done by collecting info
	 * from Musicbrainz, but this may change in the future to allow different tagging providers
	 * @param discId The discId computed from the physical CD
	 * @return A list of disc that correspond to this discid. The disc contains minimum information to be displayed to the user so
	 * a choice can be made.
	 */
	public List<Disc> lookupFromDiscId(String discId){
		log.info("starting lookup from discid:" + discId);
		return taggingProviders.get(0).lookUpFromDiscId(discId);
	}
}
