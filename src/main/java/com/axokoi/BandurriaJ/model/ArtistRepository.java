package com.axokoi.BandurriaJ.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ArtistRepository extends CrudRepository<Artist, Long> {
	List<Artist> findByNameContainingIgnoreCase(String discName);
}
