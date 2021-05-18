package com.axokoi.bandurriaj.gui.viewer.controllers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.axokoi.bandurriaj.services.dataaccess.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Searchable;
import com.axokoi.bandurriaj.model.Track;
import com.axokoi.bandurriaj.gui.viewer.views.SmartSearchView;

@Component
public class SmartSearchController {
	@Autowired
	private SmartSearchView smartSearchView;

	@Autowired
	private ArtistController artistController;
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
	private CatalogueService catalogueService;
	@Autowired
	private MusicGenreService musicGenreService;

	@Transactional
	public void smartSearch(String inputSearch) {
		List<Searchable> results = new ArrayList<>();
		//search catalogues
		results.addAll(catalogueService.smartSearch(inputSearch));
		//search discs
		results.addAll(discService.smartSearch(inputSearch));
		//search artist
		results.addAll(artistService.smartSearch(inputSearch));
		//search Track
		results.addAll(trackService.smartSearch(inputSearch));
		//Search CDs from Genre
		results.addAll(musicGenreService.smartSearch(inputSearch));

		results = results.stream().sorted(Comparator.comparing(x -> x.getClass().toString())
		.thenComparing(x->((Searchable)x).getName()))
				.collect(Collectors.toList());
		smartSearchView.refresh(results);
	}

	public void dispatchRefreshToController(Searchable selectedItem) {

		if(selectedItem == null){
			return;
		}
		if (selectedItem instanceof Disc) {
			discController.displayViewCenter((Disc) selectedItem);
		} else if (selectedItem instanceof Artist) {
			artistController.displayViewCenter((Artist) selectedItem);
		} else if (selectedItem instanceof Track) {
			discController.displayViewCenter(discService.findCdByTrack((Track) selectedItem));
		} else if (selectedItem instanceof Catalogue) {
			catalogueController.focus((Catalogue) selectedItem);
		}

	}

}
