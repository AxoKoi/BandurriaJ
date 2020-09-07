package com.axokoi.BandurriaJ.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<Track, Long> {

	List<Track> findByNameContainingIgnoringCase(String inputSearch);

	List<Track> findByCommentContainingIgnoringCase(String inputSearch);
}
