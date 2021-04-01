package com.axokoi.bandurriaj.services.tagging;

import com.axokoi.bandurriaj.model.Disc;

import java.util.List;
import java.util.Optional;

public interface ProviderFacade {

	List<Disc> lookUpFromDiscId(String discId);

	Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String providerIdentifier);
}
