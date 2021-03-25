package com.axokoi.bandurriaj.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
	List<Artist> findByNameContainingIgnoreCase(String discName);

	List<Artist> findByCommentContainingIgnoreCase(String inputSearch);

	Optional<Artist> findByMbIdentifier(String identifier);
}
