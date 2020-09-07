package com.axokoi.BandurriaJ.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.ArtistRepository;
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
	ArtistRepository artistRepository;

	public void smartSearch(String inputSearch) {
		List<Searchable> results = new ArrayList<>();
		//search catalogues

		//search discs
		results.addAll(discRepository.findByNameContaining(inputSearch));
		//search artist
		results.addAll(artistRepository.findByNameContaining(inputSearch));
		//search Band

		smartSearchView.refresh(results);
	}
}
