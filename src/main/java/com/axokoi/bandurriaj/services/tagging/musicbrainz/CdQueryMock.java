package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Profile("isolated")
@Component
public class CdQueryMock implements CdQuery {

   private static final Faker facker = new Faker();
   private static final Random random = new Random();

   @Override
   public Disc getDiscInfo(String discName) {

      return buildDisc(discName);
   }

   @Override
   public List<Disc> getDiscInfoById(String id) {
      return List.of(buildDisc( facker.elderScrolls().city()), buildDisc(facker.elderScrolls().region()));
   }

   private Disc buildDisc(String discName) {
      Disc disc = new Disc();
      disc.setName(discName);

      Band band = buildBand(disc);
      disc.setBand(band);
      disc.setComment(facker.hobbit().quote());

      disc.setTracks(buildTracks());
      return disc;
   }

   private Band buildBand(Disc disc) {
      Band band = new Band();
      band.setName(facker.rockBand().name());
      band.setDiscs(List.of(disc));
      band.setArtists(buildArtist());
      band.setComment(facker.yoda().quote());
      return band;
   }

   private List<Artist> buildArtist() {
      List<Artist> artists = new ArrayList<>();
      int numberOfArtist=random.nextInt(6);

      for(int i =0; i < numberOfArtist; i++){
         Artist artist = new Artist();
         artist.setComment(facker.twinPeaks().quote());
         artist.setName(facker.name().fullName());
         artist.setRole(facker.music().instrument());
         artists.add(artist);
      }
      return artists;
   }

   private List<Track> buildTracks() {
      List<Track> tracks = new ArrayList<>();
      int numberOfTracks = random.nextInt(12);

      for(int i = 0; i< numberOfTracks;i++)
      {
         Track track = new Track();
         track.setName(facker.funnyName().name());
         track.setDuration(Integer.toString(facker.number().numberBetween(60,600)));
         track.setComment(facker.lebowski().quote());
         tracks.add(track);
      }
      return tracks;
   }

}
