package com.axokoi.bandurriaj.services.tagging.musicbrainz.converter;

import com.axokoi.bandurriaj.model.*;
import com.axokoi.bandurriaj.services.tagging.musicbrainz.imagequery.ReleaseImageQuery;
import lombok.extern.slf4j.Slf4j;
import org.musicbrainz.MBWS2Exception;
import org.musicbrainz.model.NameCreditWs2;
import org.musicbrainz.model.RelationWs2;
import org.musicbrainz.model.TagWs2;
import org.musicbrainz.model.TrackWs2;
import org.musicbrainz.model.entity.ArtistWs2;
import org.musicbrainz.model.entity.EntityWs2;
import org.musicbrainz.model.entity.ReleaseWs2;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CdConverter implements Converter<ReleaseWs2, Disc> {

    private final TrackConverter trackConverter;
    private final ArtistConverter artistConverter;
    private final ReleaseImageQuery imageQuery;

    public CdConverter(TrackConverter trackConverter, ArtistConverter artistConverter, ReleaseImageQuery imageQuery) {
        this.trackConverter = trackConverter;
        this.artistConverter = artistConverter;
        this.imageQuery = imageQuery;
    }

    public Disc convertSimple(ReleaseWs2 releaseWs2){
        return buildMinimalDisc(releaseWs2);
    }

    @Override
    public Disc convert(ReleaseWs2 release) {
        Disc disc = buildMinimalDisc(release);

        org.musicbrainz.controller.Release controller = new org.musicbrainz.controller.Release();

        controller.getIncludes().setMedia(true);
        try {
             release = controller.getComplete(release);
        } catch (MBWS2Exception e) {
            log.error("Error when retrieving information for:" + disc.getName() + " A minimal CD will be generated.");
            return disc;
        }

        Set<Artist> relatedArtists  = extractRelatedArtistFromRelease(release);
        disc.setRelatedArtist(relatedArtists);
        disc.setTracks(getTrackList(release));
        disc.setDiscId(release.getMediumList().getCompleteDiscList().stream().findAny().orElseThrow().getDiscId());
        imageQuery.downloadFrontImage(release.getId()).ifPresent(disc::setPathToImage);

        List<TagWs2> tags = release.getReleaseGroup().getTags();
        Set<MusicGenre> genres = tags.stream().map(TagWs2::getName).map(MusicGenre::new).collect(Collectors.toSet());
        disc.setGenres(genres);

        return disc;
    }

    private Set<Artist> extractRelatedArtistFromRelease(ReleaseWs2 release) {
        if (release == null) {
            return Collections.emptySet();
        }

            return release.getMediumList().getCompleteTrackList().stream()
                    .flatMap(trackWs2 -> trackWs2.getRecording().getRelationList().getRelations().stream().map(RelationWs2::getTarget))
                    .filter(ArtistWs2.class::isInstance)
                    .map(ArtistWs2.class::cast)
                    //Collect artist with the same id from different tracks
                    .collect(Collectors.groupingBy(EntityWs2::getId))
                    .values()
                    .stream()
                    //get first element, as all are the same
                    .map(l->l.get(0))
                    .map(artistConverter::convert).collect(Collectors.toSet());
    }

    private Disc buildMinimalDisc(ReleaseWs2 release) {
        Disc disc = new Disc();
        disc.setName(release.getTitle());
        disc.setExternalIdentifier(getExternalIdentifiers(release));
        disc.setCreditedArtists(release.getArtistCredit().getNameCredits().stream().map(NameCreditWs2::getArtist)
                .map(artistConverter::convert).collect(Collectors.toSet()));
        disc.setComment(release.getYear());
        disc.setDiscId(release.getMediumList().getCompleteDiscList().stream().findAny().orElseThrow().getDiscId());
        return disc;
    }

    private Set<ExternalIdentifier> getExternalIdentifiers(ReleaseWs2 release) {
        Set<ExternalIdentifier> externalIdentifiers = new HashSet<>();
        ExternalIdentifier identifier = new ExternalIdentifier();
        identifier.setType(ExternalIdentifier.Type.MUSICBRAINZ);
        identifier.setIdentifier(release.getId());
        externalIdentifiers.add(identifier);
        return externalIdentifiers;
    }

    private Set<Track> getTrackList(ReleaseWs2 release) {
        List<TrackWs2> tracks = release.getMediumList().getCompleteTrackList();
        return tracks.stream().map(trackConverter::convert).collect(Collectors.toSet());
    }


}
