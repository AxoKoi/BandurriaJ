package com.axokoi.BandurriaJ.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DiscRepository extends CrudRepository<Disc, Long> {
	Disc findByName(String discName);
	//todo should discName be unique? maybe we could display a disc with it's id?
	List<Disc> findByNameContaining(String discName);
}
