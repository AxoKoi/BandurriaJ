package com.axokoi.bandurriaj.services.tagging.musicbrainz.imageQuery;

import java.util.Optional;

public interface ReleaseImageQuery {
   /**
    *  Return an optional with the local full path where the image was downloaded.
    *  If te image is not found, the optional is empty.
    * @param mbIdentifier The MusicBrainz unique identifier of the release
    * @return
    */
   Optional<String> downloadFrontImage(String mbIdentifier);

}
