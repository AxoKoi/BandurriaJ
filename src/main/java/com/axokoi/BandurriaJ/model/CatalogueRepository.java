package com.axokoi.BandurriaJ.model;

import org.springframework.data.repository.CrudRepository;

public interface CatalogueRepository extends CrudRepository<Catalogue, Long> {

	Long deleteByName(String name);
}
