package com.axokoi.BandurriaJ.Controllers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.ArtistService;
import com.axokoi.BandurriaJ.model.BandRepository;
import com.axokoi.BandurriaJ.model.DiscRepository;
import com.axokoi.BandurriaJ.model.Searchable;
import com.axokoi.BandurriaJ.views.SmartSearchView;

@Component
public class SmartSearchController {
	@Autowired
	SmartSearchView smartSearchView;

	@Autowired
	DiscRepository discRepository;
	@Autowired
	ArtistService artistService;
	@Autowired
	BandRepository bandRepository;

	@Transactional
	public void smartSearch(String inputSearch) {
		List<Searchable> results = new ArrayList<>();
		//search catalogues

		//search discs
		results.addAll(discRepository.findByNameContainingIgnoreCase(inputSearch));
		//search artist
		results.addAll(artistService.smartSearch(inputSearch));
		//search Band
		results.addAll(bandRepository.findByNameContainingIgnoreCase(inputSearch));

		smartSearchView.refresh(results);
	}
}
