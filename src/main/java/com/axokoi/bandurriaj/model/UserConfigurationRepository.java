package com.axokoi.bandurriaj.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserConfigurationRepository extends CrudRepository<UserConfiguration,Long> {

   public Optional<UserConfiguration> findByKey (String key);
}
