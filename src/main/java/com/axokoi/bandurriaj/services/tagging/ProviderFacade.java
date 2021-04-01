package com.axokoi.bandurriaj.services.tagging;

import com.axokoi.bandurriaj.model.Disc;

import java.util.List;
import java.util.Optional;

public interface ProviderFacade {

	Disc getDiscInfo(String discName);

	List<Disc> getDiscInfoFromDiscId(String id);

	Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String providerIdentifier);

	List<Disc> lookUpFromDiscId(String discId);
}
