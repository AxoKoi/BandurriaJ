package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Band;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Profile("isolated")
@Component
public class CdQueryMock implements CdQuery {


   @Override
   public Disc getDiscInfo(String discName) {

      return buildDisc(discName);
   }


   @Override
   public List<Disc> getDiscInfoById(String id) {
      return List.of(buildDisc("cd - 1 "), buildDisc("cd - 2"));
   }

   private Disc buildDisc(String discName) {
      Disc disc = new Disc();
      disc.setName(discName);

      Band band = buildBand(discName, disc);
      disc.setBand(band);
      disc.setComment("This is a default comment");

      disc.setTracks(buildTracks(discName));
      return disc;
   }


   private Band buildBand(String discName, Disc disc) {
      Band band = new Band();
      band.setName("Creators of " + discName);
      band.setDiscs(List.of(disc));
      band.setArtists(buildArtist());
      band.setComment("What a Band!");
      return band;
   }

   private List<Artist> buildArtist() {
      List<Artist> artists = new ArrayList<>();

      Artist artist1 = new Artist();
      artist1.setComment("The first artist.");
      artist1.setName("Artist name 1");
      artist1.setRole("Role 1");
      artists.add(artist1);

      Artist artist2 = new Artist();
      artist2.setComment("The second artist.");
      artist2.setName("Artist name 2");
      artist2.setRole("Role 2");
      artists.add(artist2);
      return artists;
   }

   private List<Track> buildTracks(String discName) {
      List<Track> tracks = new ArrayList<>();
      Track track1 = new Track();
      track1.setName(discName + " - Track 1");
      track1.setDuration("2:15");
      track1.setComment("Track 1 was created during the 2005 tour");
      tracks.add(track1);

      Track track2 = new Track();
      track2.setName(discName + " - Track 2");
      track2.setDuration("3:55");
      track2.setComment("Track 2 is a remake of the 2001 version");
      tracks.add(track2);
      return tracks;
   }

}
