package com.axokoi.BandurriaJ.services.dataaccess;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Band;
import com.axokoi.BandurriaJ.model.BandRepository;

@Component
public class BandService implements SmartSearchService<Band> {
	private static BandRepository bandRepository;

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
