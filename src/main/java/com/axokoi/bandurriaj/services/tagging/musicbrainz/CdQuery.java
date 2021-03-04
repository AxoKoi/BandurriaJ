package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;

import java.util.List;

public interface CdQuery {
   Disc getDiscInfo(String discName);
   List<Disc> getDiscInfoById(String id);
}
