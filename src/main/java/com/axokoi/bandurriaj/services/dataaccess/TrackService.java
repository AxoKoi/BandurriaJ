package com.axokoi.bandurriaj.services.dataaccess;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.axokoi.bandurriaj.model.Track;
import com.axokoi.bandurriaj.model.TrackRepository;

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

	public void save(Track track){
		trackRepository.save(track);
	}

	public void delete(Track track) {
		trackRepository.delete(track);
	}
}
