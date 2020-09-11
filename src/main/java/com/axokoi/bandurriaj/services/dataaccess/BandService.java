package com.axokoi.bandurriaj.services.dataaccess;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.BandRepository;

@Component
public class BandService implements SmartSearchService<Band> {
	private BandRepository bandRepository;

	public BandService(BandRepository bandRepository) {
		this.bandRepository = bandRepository;
	}

	@Transactional
	public Band findById(Long id) {
		Band band = bandRepository.findById(id).orElseThrow();
		//Fetch Lazy
		band.getDiscs().size();
		band.getArtists();
		return band;
	}

	@Override
	public List<Band> smartSearch(String inputSearch) {
		List<Band> bands = bandRepository.findByNameContainingIgnoreCase(inputSearch);
		bands.addAll(bandRepository.findByCommentContainingIgnoreCase(inputSearch));
		return bands;
	}

}
