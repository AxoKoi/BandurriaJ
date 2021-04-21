package com.axokoi.bandurriaj.model;

import java.util.Optional;

public interface BusinessEntityRepository<T>  {
   Optional<T> findByBusinessIdentifier(String businessIdentifier);
}
