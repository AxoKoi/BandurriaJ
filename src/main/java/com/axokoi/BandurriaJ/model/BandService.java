package com.axokoi.BandurriaJ.model;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

@Component
public class BandService {
	private static BandRepository bandRepository;

	public BandService(BandRepository bandRepository) {
		this.bandRepository = bandRepository;
	}

	//todo these services can be probably be removed, to decide
	@Transactional
	public Band findById(Long id) {
		Band band = bandRepository.findById(id).orElseThrow();
		//Fetch Lazy
		band.getDiscs().size();
		band.getArtists();
		return band;
	}
}
