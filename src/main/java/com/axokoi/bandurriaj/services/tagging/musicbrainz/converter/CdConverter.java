package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.model.Track;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.model.NameCreditWs2;
import org.musicbrainz.model.RelationWs2;
import org.musicbrainz.model.TrackWs2;
import org.musicbrainz.model.entity.ArtistWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CdConverter implements Converter<ReleaseWs2, Disc> {

    private final TrackConverter trackConverter;
    private final ArtistConverter artistConverter;

    public CdConverter(TrackConverter trackConverter, ArtistConverter artistConverter) {
        this.trackConverter = trackConverter;
        this.artistConverter = artistConverter;
    }

    @Override
    public Disc convert(ReleaseWs2 release) {
        Disc disc = buildDisc(release);

        org.musicbrainz.controller.Release controller = new org.musicbrainz.controller.Release();

        controller.getIncludes().setMedia(true);
        try {
             release = controller.getComplete(release);
        } catch (MBWS2Exception e) {
            e.printStackTrace();//IRO add logger instead
        }
//IRO add generic popup service

        Set<Artist> relatedArtists  = extractRelatedArtistFromRelease(release);
        disc.setRelatedArtist(relatedArtists);
        disc.setTracks(getTrackList(release));

        return disc;
    }

    private Set<Artist> extractRelatedArtistFromRelease(ReleaseWs2 release) {
        if (release == null) {
            return Collections.emptySet();
        }
            return release.getMediumList().getCompleteTrackList().stream()
                    .flatMap(trackWs2 -> trackWs2.getRecording().getRelationList().getRelations().stream().map(RelationWs2::getTarget))
                    .filter(entityWs2 -> entityWs2 instanceof ArtistWs2)
                    .map(ArtistWs2.class::cast)
                    .map(artistConverter::convert).collect(Collectors.toSet());
    }

    private Disc buildDisc(ReleaseWs2 release) {
        Disc disc = new Disc();
        disc.setName(release.getTitle());

        disc.setCreditedArtists(release.getArtistCredit().getNameCredits().stream().map(NameCreditWs2::getArtist)
                .map(artistConverter::convert).collect(Collectors.toSet()));
        disc.setComment(release.getYear());
        return disc;
    }

    private Set<Track> getTrackList(ReleaseWs2 release) {
        List<TrackWs2> tracks = release.getMediumList().getCompleteTrackList();
        return tracks.stream().map(trackConverter::convert).collect(Collectors.toSet());
    }


}
