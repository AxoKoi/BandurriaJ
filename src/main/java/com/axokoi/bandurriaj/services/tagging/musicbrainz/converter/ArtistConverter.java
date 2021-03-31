package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.Artist;
import org.musicbrainz.model.entity.ArtistWs2;
import org.springframework.stereotype.Component;

//IRO Improve this
@Component
public class ArtistConverter implements Converter<ArtistWs2, Artist> {
   @Override
   public Artist convert(ArtistWs2 artistWs2) {
      if (artistWs2 == null) {
         return null;
      }
      Artist artist = new Artist();
      artist.setName(artistWs2.getUniqueName());
      if (artistWs2.getType() == null) {
         artist.setType(Artist.Type.UNKNOWN);
      } else {
         artist.setType(artistWs2.getType().contains("Person") ? Artist.Type.SINGLE : Artist.Type.COMPOSITE);
      }
      //IRO check if this is the id
      artist.setMbIdentifier(artistWs2.getId());
      return artist;
   }
}
