package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.*;

@Profile("noInternet")
@Component
public class CdQueryMock implements CdQuery {

   private static final Faker facker = new Faker();
   private static final Random random = new Random();

   @Override
   public List<Disc> lookUpFromDiscId(String discId) {
      return Collections.emptyList();
   }

   @Override
   public Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String uniqueId) {
      return Optional.empty();
   }

   private Disc buildDisc(String discName) {
      Disc disc = new Disc();
      disc.setName(discName);


      disc.setCreditedArtists(buildArtist());
      disc.setComment(facker.hobbit().quote());

      disc.setTracks(buildTracks());
      return disc;
   }



   private Set<Artist> buildArtist() {
      Set<Artist> artists = new HashSet<>();
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

   private Set<Track> buildTracks() {
      Set<Track> tracks = new HashSet<>();
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
