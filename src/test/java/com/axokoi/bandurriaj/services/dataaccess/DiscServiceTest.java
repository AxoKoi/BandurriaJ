package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.*;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DiscServiceTest {


   @Autowired
   private DataSource dataSource;
   @Autowired
   private JdbcTemplate jdbcTemplate;
   @Autowired
   private EntityManager entityManager;
   @Autowired
   private DiscRepository discRepository;
   @Autowired
   private ArtistRepository artistRepository;
   @Autowired
   private TrackRepository trackRepository;
   @Autowired
   private CatalogueRepository catalogueRepository;

   private CatalogueService catalogueService;
   private DiscService discService;

   @Before
   public void setup() {
      catalogueService = new CatalogueService(catalogueRepository);
      discService = new DiscService(trackRepository, discRepository, catalogueService);
   }

   @Test
   public void deleteCD() {

      //Given
      Disc disc = buildCD();
      disc.getAllArtist().forEach(artist -> artistRepository.save(artist));
      discRepository.save(disc);
      Assert.assertNotNull(discRepository.findByBusinessIdentifier(disc.getBusinessIdentifier()).get());

      //when
      discService.deleteDisc(disc);

      //Then
      Assert.assertTrue(discRepository.findByBusinessIdentifier(disc.getBusinessIdentifier()).isEmpty());
      Optional<Artist> optionalArtist = artistRepository.findByBusinessIdentifier(disc.getAllArtist().stream().findFirst().get().getBusinessIdentifier());
      Assert.assertTrue(optionalArtist.isPresent());
      Assert.assertEquals("TestArtist", optionalArtist.get().getName());
      Assert.assertTrue(IterableUtils.toList(trackRepository.findAll()).isEmpty());
   }

   @Test
   public void findByExternalIdentifier() {
      //given
      Disc disc = buildCD();
      disc.getAllArtist().forEach(artist -> artistRepository.save(artist));
      ExternalIdentifier externalIdentifier = new ExternalIdentifier();
      externalIdentifier.setType(ExternalIdentifier.Type.MUSICBRAINZ);
      externalIdentifier.setIdentifier("#AZE3");
      disc.addExternalIdentifier(externalIdentifier);
      discRepository.save(disc);

      //when
      Optional<Disc> discByExternalIdentifier = discService.findByExternalIdentifier(externalIdentifier);

      //then
      Assert.assertTrue(discByExternalIdentifier.isPresent());
   }

   @Test
   public void addTrackToCD() {
      //Given
      Disc disc = buildCD();
      discRepository.save(disc);
      Track newTrack = new Track();
      newTrack.setName("new Track");
      newTrack.setDuration("2:56");
      newTrack.setNumber(0);
      //when
      Disc updatedDisc = discService.addTrackToCd(disc, newTrack);

      //Then
      Assert.assertEquals(2, updatedDisc.getTracks().size());
      Assert.assertTrue(updatedDisc.getTracks().contains(newTrack));

   }

   @Test
   public void deleteTrackFromCD() {
      //Given
      Disc disc = buildCD();
      discRepository.save(disc);
      artistRepository.saveAll(disc.getAllArtist());
      Track track = (Track) disc.getTracks().toArray()[0];

      //when
      Disc updatedDisc = discService.deleteTrackFromCD(disc, track);

      //Then
      Assert.assertEquals(0, updatedDisc.getTracks().size());
      Assert.assertTrue(trackRepository.findByBusinessIdentifier(track.getBusinessIdentifier()).isEmpty());
   }

   private Disc buildCD() {
      Disc disc = new Disc();
      disc.setDiscId("123456");
      Set<Artist> artists = new HashSet<>();
      Artist artist = new Artist();
      artist.setName("TestArtist");
      artists.add(artist);
      disc.setCreditedArtists(artists);

      Set<Track> tracks = new HashSet<>();
      Track track = new Track();
      track.setNumber(1);
      tracks.add(track);
      disc.setTracks(tracks);
      return disc;
   }

}