package com.axokoi.bandurriaj.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<Track, Long>,BusinessEntityRepository<Track> {

	List<Track> findByNameContainingIgnoringCase(String inputSearch);

	List<Track> findByCommentContainingIgnoringCase(String inputSearch);
}
