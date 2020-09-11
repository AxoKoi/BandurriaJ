package com.axokoi.bandurriaj.Controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.CatalogueRepository;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.views.CatalogueView;

@Component
public class CatalogueController {
	@Autowired
	private CatalogueRepository catalogueRepository;
	@Autowired
	private CatalogueView catalogueView;
	@Autowired
	private DiscController discController;

	public List<Catalogue> getCatalogues() {
		return IterableUtils.toList(catalogueRepository.findAll());
	}

	public void addNewCatalogue(String catalogueName) {
		Catalogue newCatalogue = new Catalogue();
		newCatalogue.setName(catalogueName);
		catalogueRepository.save(newCatalogue);
		catalogueView.refresh();
	}

	@Transactional
	public void deleteCatalogue(String catalogueName) {
		catalogueRepository.deleteByName(catalogueName);
		catalogueView.refresh();
	}

	public void focus(Catalogue catalogue) {
		catalogueView.focus(catalogue);
	}

	public void dispatchRefreshDiscView(Disc disc) {
		discController.refreshView(disc);
	}
}
