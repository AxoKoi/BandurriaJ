package com.axokoi.BandurriaJ.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.ArtistService;
import com.axokoi.BandurriaJ.model.BandService;
import com.axokoi.BandurriaJ.model.CatalogueService;
import com.axokoi.BandurriaJ.model.DiscService;
import com.axokoi.BandurriaJ.model.Searchable;
import com.axokoi.BandurriaJ.model.TrackService;
import com.axokoi.BandurriaJ.views.SmartSearchView;

@Component
public class SmartSearchController {
	@Autowired
	SmartSearchView smartSearchView;

	@Autowired
	DiscService discService;
	@Autowired
	ArtistService artistService;
	@Autowired
	BandService bandService;

	@Autowired
	CatalogueService catalogueService;

	@Autowired
	TrackService trackService ;

	@Transactional
	public void smartSearch(String inputSearch) {
		List<Searchable> results = new ArrayList<>();
		//search catalogues
		results.addAll(catalogueService.smartSearch(inputSearch));
		//search discs
		results.addAll(discService.smartSearch(inputSearch));
		//search artist
		results.addAll(artistService.smartSearch(inputSearch));
		//search Band
		results.addAll(bandService.smartSearch(inputSearch));
		//search Track
		results.addAll(trackService.smartSearch(inputSearch));
		smartSearchView.refresh(results);
	}
}
