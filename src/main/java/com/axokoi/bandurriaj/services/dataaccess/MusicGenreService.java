package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.model.MusicGenre;
import com.axokoi.bandurriaj.model.MusicGenreRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MusicGenreService implements SmartSearchService<Disc> {

   private final MusicGenreRepository repository;
   private final DiscRepository discRepository;

   public MusicGenreService(MusicGenreRepository repository, DiscRepository discRepository) {
      this.repository = repository;
      this.discRepository = discRepository;
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


   @Override
   public List<Disc> smartSearch(String inputSearch) {

      return IterableUtils.toList(discRepository.findAll()).stream()
              .filter(disc->disc.getGenres().stream()
                      .map(MusicGenre::getName)
                      .anyMatch(genreName->genreName.trim().toLowerCase(Locale.ROOT).contains(inputSearch.toLowerCase(Locale.ROOT))))
      .collect(Collectors.toList());
         }
}
