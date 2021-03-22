package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.Artist;
import org.musicbrainz.model.entity.ArtistWs2;
import org.springframework.stereotype.Component;

@Component
public class ArtistConverter implements Converter<ArtistWs2, Artist> {
   @Override
   public Artist convert(ArtistWs2 artistWs2) {
      if (artistWs2 == null) {
         return null;
      }
      Artist artist = new Artist();
      artist.setName(artistWs2.getUniqueName());
      //IRO type can be null?
      artist.setType(artistWs2.getType().contains("Person") ? Artist.Type.SINGLE : Artist.Type.GROUP);
      //IRO check if this is the id
      artist.setMbIdentifier(artistWs2.getId());
      return artist;
   }
}
