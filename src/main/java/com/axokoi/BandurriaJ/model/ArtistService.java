package com.axokoi.BandurriaJ.model;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class ArtistService {


	private static ArtistRepository artistRepository;

	public ArtistService(ArtistRepository artistRepository) {
		this.artistRepository = artistRepository;
	}

	public static Optional<Artist> getById(Long id){
	return artistRepository.findById(id);
}
}
