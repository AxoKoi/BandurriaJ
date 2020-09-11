package com.axokoi.bandurriaj.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CatalogueRepository extends CrudRepository<Catalogue, Long> {

	Long deleteByName(String name);

	List<Catalogue> searchByNameContainingIgnoringCase(String inputSearch);
}
