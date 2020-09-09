package com.axokoi.BandurriaJ.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.services.dataaccess.ArtistService;
import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.services.dataaccess.BandService;
import com.axokoi.BandurriaJ.model.Catalogue;
import com.axokoi.BandurriaJ.services.dataaccess.CatalogueService;
import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.services.dataaccess.DiscService;
import com.axokoi.BandurriaJ.model.Searchable;
import com.axokoi.BandurriaJ.model.Track;
import com.axokoi.BandurriaJ.services.dataaccess.TrackService;
import com.axokoi.BandurriaJ.views.SmartSearchView;

@Component
public class SmartSearchController {
	@Autowired
	private SmartSearchView smartSearchView;

	@Autowired
	private ArtistController artistController;
	@Autowired
	private BandController bandController;
	@Autowired
	private CatalogueController catalogueController;
	@Autowired
	private DiscController discController;

	@Autowired
	private TrackService trackService;
	@Autowired
	private DiscService discService;
	@Autowired
	private ArtistService artistService;
	@Autowired
	private BandService bandService;
	@Autowired
	private CatalogueService catalogueService;


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

	public void dispatchRefreshToController(Searchable selectedItem) {

		if (selectedItem instanceof Disc) {
			discController.refreshView((Disc) selectedItem);
		} else if (selectedItem instanceof Artist) {
			artistController.refreshView((Artist) selectedItem);
		} else if (selectedItem instanceof Band) {
			bandController.refreshView((Band) selectedItem);
		} else if (selectedItem instanceof Track) {
			discController.refreshView(discService.findCdByTrack((Track) selectedItem));
		} else if (selectedItem instanceof Catalogue) {
			catalogueController.focus((Catalogue) selectedItem);
		}

	}

}
