package com.axokoi.BandurriaJ.services.dataaccess;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.axokoi.BandurriaJ.model.Artist;
import com.axokoi.BandurriaJ.model.ArtistRepository;

@Component
public class ArtistService implements SmartSearchService<Artist> {

	private static ArtistRepository artistRepository;

	public ArtistService(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	public static Optional<Artist> getById(Long id) {
		return artistRepository.findById(id);
	}

	@Override
	public List<Artist> smartSearch(String inputSearch) {
		List<Artist> result = artistRepository.findByNameContainingIgnoreCase(inputSearch);
		result.addAll(artistRepository.findByCommentContainingIgnoreCase(inputSearch));
		return result;
	}

	public Artist findById(Long id) {
		return artistRepository.findById(id).orElseThrow();
	}
}
