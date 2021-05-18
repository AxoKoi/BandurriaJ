package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.DiscRepository;
import com.axokoi.bandurriaj.model.MusicGenre;
import com.axokoi.bandurriaj.model.MusicGenreRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MusicGenreServiceTest {

   MusicGenreService musicGenreService;

   @Autowired
   MusicGenreRepository musicGenreRepository;
   @Autowired
   DiscRepository discRepository;
   @Autowired
   private DataSource dataSource;
   @Autowired
   private JdbcTemplate jdbcTemplate;
   @Autowired
   private EntityManager entityManager;

   @Before
   public void setup() {
      musicGenreService = new MusicGenreService(musicGenreRepository, discRepository);
   }

   @Test
   public void findByName() {
      //given
      MusicGenre jazzy = new MusicGenre("jazzy");
      musicGenreRepository.save(jazzy);

      //when
      Optional<MusicGenre> genreFromDB = musicGenreService.findByName("jazzy");

      //then
      assertTrue(genreFromDB.isPresent());
      assertEquals("jazzy", genreFromDB.get().getName());
   }

   @Test
   public void findByNameAddIfNotPresent() {
      //given
      Optional<MusicGenre> newGenre = musicGenreService.findByName("newGenre");
      assertTrue(newGenre.isEmpty());

      //when
      MusicGenre genreFromDB = musicGenreService.findByNameAddIfNotPresent("newGenre");

      //then
      assertNotNull(genreFromDB);
      assertEquals("newGenre", genreFromDB.getName());

   }
}