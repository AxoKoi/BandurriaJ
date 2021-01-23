package com.axokoi.bandurriaj.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BandRepository extends CrudRepository<Band, Long> {

    List<Band> findByNameContainingIgnoreCase(String inputSearch);

    List<Band> findByCommentContainingIgnoreCase(String inputSearch);

    Optional<Band> findByNameIgnoreCase(String name);
}
