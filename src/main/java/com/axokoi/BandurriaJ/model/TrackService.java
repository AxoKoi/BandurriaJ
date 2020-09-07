package com.axokoi.BandurriaJ.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackService implements SmartSearchService<Track> {
	@Autowired
	TrackRepository trackRepository;

	@Override
	public List<Track> smartSearch(String inputSearch) {
		List<Track> results = trackRepository.findByNameContainingIgnoringCase(inputSearch);
		results.addAll(trackRepository.findByCommentContainingIgnoringCase(inputSearch));
		return results;
	}
}
