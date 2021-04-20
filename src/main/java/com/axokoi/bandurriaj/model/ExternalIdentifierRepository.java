package com.axokoi.bandurriaj.model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ExternalIdentifierRepository extends CrudRepository<ExternalIdentifier,Long> {
   List<ExternalIdentifier> findAllByType(ExternalIdentifier.Type user);

   Optional<ExternalIdentifier> findByTypeAndIdentifier(ExternalIdentifier.Type type, String identifier);
}
