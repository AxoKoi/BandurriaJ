package com.axokoi.BandurriaJ.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BandRepository extends CrudRepository<Band, Long> {

	List<Band> findByNameContainingIgnoreCase(String inputSearch);
}
