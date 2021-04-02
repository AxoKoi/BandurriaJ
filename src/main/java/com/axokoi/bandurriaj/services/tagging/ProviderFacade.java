package com.axokoi.bandurriaj.services.tagging;

import com.axokoi.bandurriaj.model.Disc;

import java.util.List;
import java.util.Optional;

public interface ProviderFacade {

	/**
	 * A lookup method implementation from this external provider from the discID of a CD, as computed by libDiscId.
	 * @param discId The discId computed from the physical CD
	 * @return A list of disc that correspond to this discid. The disc contains minimum information to be displayed to the user so
	 * a choice can be made.
	 */
	List<Disc> lookUpFromDiscId(String discId);

	/**
	 *  The entry point for retrieving a disc with all the related information from this given external tagging provider.
	 * @param providerIdentifier The external identifier  corresponding to the disc.
	 * @return An optional Disc which will be empty if the disc couldn't been found.
	 */
	Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String providerIdentifier);
}
