package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;

import java.util.List;
import java.util.Optional;

public interface CdQuery {
   List<Disc> getDiscInfoById(String id);
   List<Disc> lookUpFromDiscId(String discId);
   Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String uniqueId);

}
