package com.axokoi.BandurriaJ.Controllers;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Catalogue;
import com.axokoi.BandurriaJ.model.CatalogueRepository;

@Component
public class CatalogueController {
	private final CatalogueRepository catalogueRepository;

	public CatalogueController(CatalogueRepository catalogueRepository) {
		this.catalogueRepository = catalogueRepository;
	}

	public List<Catalogue> getCatalogues() {
		return IterableUtils.toList(catalogueRepository.findAll());
	}
}
