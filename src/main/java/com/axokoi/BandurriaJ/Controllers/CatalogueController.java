package com.axokoi.BandurriaJ.Controllers;

import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Catalogue;
import com.axokoi.BandurriaJ.model.CatalogueRepository;
import com.axokoi.BandurriaJ.views.CatalogueView;

@Component
public class CatalogueController {
	private final CatalogueRepository catalogueRepository;

	@Autowired
	CatalogueView catalogueView;

	public CatalogueController(CatalogueRepository catalogueRepository) {
		this.catalogueRepository = catalogueRepository;
	}

	public List<Catalogue> getCatalogues() {
		return IterableUtils.toList(catalogueRepository.findAll());
	}

	public void addNewCatalogue(String catalogueName) {
		Catalogue newCatalogue = new Catalogue();
		newCatalogue.setName(catalogueName);
		catalogueRepository.save(newCatalogue);
		catalogueView.update();
	}
}
