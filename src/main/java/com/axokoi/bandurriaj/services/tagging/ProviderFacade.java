package com.axokoi.bandurriaj.services.tagging;

import com.axokoi.bandurriaj.model.Disc;

import java.util.List;

public interface ProviderFacade {

	Disc getDiscInfo(String discName);

	List<Disc> getDiscInfoFromDiscId(String id);
}
