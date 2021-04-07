package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(MockitoJUnitRunner.class)
class MusicBrainzFacadeTest {

    @Mock
    CdQuery cdQuery;

    @InjectMocks
    private MusicBrainzFacade musicBrainzFacade;


    @BeforeEach
    public void setup() {
        initMocks(this);
    }

    @Test
    void findCdById() {
        Disc disc = new Disc();
        disc.setName("Morrison Hotel");

        Artist band = new Artist();
        band.setName("The Doors");
        List<Artist> artists = new ArrayList<>();
        Artist jimMorrison = new Artist();
        jimMorrison.setName("Jim Morrison");
        jimMorrison.setRole("Vocals");
        artists.add(jimMorrison);
        disc.setCreditedArtists(Set.of(band));

        doReturn(List.of(disc)).when(cdQuery).lookUpFromDiscId(".p4ZJ206p8mpaTvnG8.ZG9_qagE-");


        Disc discResult = musicBrainzFacade.lookUpFromDiscId(".p4ZJ206p8mpaTvnG8.ZG9_qagE-").get(0);
        assertThat(discResult.getName()).containsIgnoringCase("Morrison Hotel");
        assertThat(discResult.getCreditedArtists().toArray(new Artist[0])[0].getName()).containsIgnoringCase("Doors");
    }

}