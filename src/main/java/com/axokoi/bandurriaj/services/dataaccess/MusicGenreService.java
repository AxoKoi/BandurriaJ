package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.MusicGenre;
import com.axokoi.bandurriaj.model.MusicGenreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MusicGenreService {

   private final MusicGenreRepository repository;

   public MusicGenreService(MusicGenreRepository repository) {
      this.repository = repository;
   }

   public void save(MusicGenre genre){
      repository.save(genre);
   }

   public Optional<MusicGenre> findByName(String name) {
      return repository.findByName(name);
   }

   public MusicGenre findByNameAddIfNotPresent(String name) {
      Optional<MusicGenre> musicGenre = repository.findByName(name);
      if (musicGenre.isPresent()) {
         return musicGenre.get();
      } else {
         var newMusicGenre = new MusicGenre();
         newMusicGenre.setName(name);
         return repository.save(newMusicGenre);
      }
   }

}
