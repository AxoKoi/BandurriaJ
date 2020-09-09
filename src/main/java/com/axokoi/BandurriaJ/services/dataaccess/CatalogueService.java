package com.axokoi.BandurriaJ.services.dataaccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Catalogue;
import com.axokoi.BandurriaJ.model.CatalogueRepository;

@Component
public class CatalogueService implements SmartSearchService<Catalogue> {

	@Autowired
	CatalogueRepository catalogueRepository;
	@Override
	public List<Catalogue> smartSearch(String inputSearch) {
		return catalogueRepository.searchByNameContainingIgnoringCase(inputSearch);
	}
}
