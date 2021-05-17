package com.axokoi.bandurriaj.model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MusicGenreRepository extends CrudRepository<MusicGenre,Long> {
   Optional<MusicGenre> findByName(String name);
}
