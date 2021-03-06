package com.axokoi.bandurriaj.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DiscRepository extends CrudRepository<Disc, Long>, BusinessEntityRepository<Disc> {
   Disc findByName(String discName);

   List<Disc> findByNameContainingIgnoreCase(String discName);

   List<Disc> findByCommentContainingIgnoringCase(String inputSearch);

   Optional<Disc> findByNameIgnoreCase(String name);

   Optional<Disc> findByDiscId(String discId);
}
