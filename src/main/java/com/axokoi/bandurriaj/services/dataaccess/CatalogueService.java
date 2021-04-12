package com.axokoi.bandurriaj.services.dataaccess;

import java.util.List;

import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.CatalogueRepository;

@Component
public class CatalogueService implements SmartSearchService<Catalogue> {


	private final CatalogueRepository catalogueRepository;

	public CatalogueService(CatalogueRepository catalogueRepository) {
		this.catalogueRepository = catalogueRepository;
	}

	@Override
	public List<Catalogue> smartSearch(String inputSearch) {
		return catalogueRepository.searchByNameContainingIgnoringCase(inputSearch);
	}


	public void save(Catalogue catalogue){
		catalogueRepository.save(catalogue);
	}
}
