package com.axokoi.bandurriaj.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ExternalIdentifierRepository extends CrudRepository<ExternalIdentifier,Long> {
   List<ExternalIdentifier> findAllByType(ExternalIdentifier.Type user);
}
