package com.axokoi.BandurriaJ.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Disc;
import com.axokoi.BandurriaJ.model.DiscRepository;
import com.axokoi.BandurriaJ.views.SmartSearchView;

@Component
public class SmartSearchController {
	@Autowired
	SmartSearchView smartSearchView;

	@Autowired
	DiscRepository discRepository;

	public void smartSearch(String inputSearch) {
		List<String> results = new ArrayList<>();

		//search catalogues

		//search discs
		results.addAll(discRepository.findByNameContaining(inputSearch).stream().map(Disc::getName).collect(Collectors.toList()));
		//search artist

		//search Band

		smartSearchView.refresh(results);
	}
}
