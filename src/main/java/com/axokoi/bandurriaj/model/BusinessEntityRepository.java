package com.axokoi.bandurriaj.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BusinessEntityRepository<T>  {
   Optional<T> findByBusinessIdentifier(String businessIdentifier);
}
