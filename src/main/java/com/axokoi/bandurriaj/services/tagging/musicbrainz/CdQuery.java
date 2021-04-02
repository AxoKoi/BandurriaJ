package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;

import java.util.List;
import java.util.Optional;

public interface CdQuery {
   /**
    * Concrete implementation of {@link com.axokoi.bandurriaj.services.tagging.ProviderFacade#lookUpFromDiscId(String)}
    * @param discId The discId computed from the physical CD
    * @return A list of disc that correspond to this discid. The disc contains minimum information to be displayed to the user so
    * a choice can be made.
    */
   List<Disc> lookUpFromDiscId(String discId);

   /**
    * Concrete implementation of {@link com.axokoi.bandurriaj.services.tagging.ProviderFacade#getFullDiscInfoFromUniqueIdentifier(String)}
    * @param uniqueId The external identifier  corresponding to the disc.
    * @return An optional Disc which will be empty if the disc couldn't been found.
    */
   Optional<Disc> getFullDiscInfoFromUniqueIdentifier(String uniqueId);

}
