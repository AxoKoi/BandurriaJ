package com.axokoi.BandurriaJ.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CatalogueService implements SmartSearchService<Catalogue> {

	@Autowired
	CatalogueRepository catalogueRepository;
	@Override
	public List<Catalogue> smartSearch(String inputSearch) {
		return catalogueRepository.searchByNameContainingIgnoringCase(inputSearch);
	}
}
