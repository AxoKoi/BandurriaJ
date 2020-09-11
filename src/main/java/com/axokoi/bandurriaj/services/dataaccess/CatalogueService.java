package com.axokoi.bandurriaj.services.dataaccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.CatalogueRepository;

@Component
public class CatalogueService implements SmartSearchService<Catalogue> {

	@Autowired
	CatalogueRepository catalogueRepository;
	@Override
	public List<Catalogue> smartSearch(String inputSearch) {
		return catalogueRepository.searchByNameContainingIgnoringCase(inputSearch);
	}
}
