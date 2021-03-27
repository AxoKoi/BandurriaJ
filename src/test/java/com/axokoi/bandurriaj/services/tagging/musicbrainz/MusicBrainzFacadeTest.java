package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.model.Disc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(SpringExtension.class)
class MusicBrainzFacadeTest {

    @Mock
    CdQueryImpl cdQueryImpl;

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
        band.setComposingArtists(artists);
        disc.setArtists(Set.of(band));

        doReturn(List.of(disc)).when(cdQueryImpl).getDiscInfoById(".p4ZJ206p8mpaTvnG8.ZG9_qagE-");

        Disc discResult = musicBrainzFacade.getDiscInfoFromDiscId(".p4ZJ206p8mpaTvnG8.ZG9_qagE-").get(0);
        assertThat(discResult.getName()).containsIgnoringCase("Morrison Hotel");
        assertThat(discResult.getArtists().toArray(new Artist[0])[0].getName()).containsIgnoringCase("Doors");
        assertThat(discResult.getArtists().toArray(new Artist[0])[0].getComposingArtists().get(0).getName()).containsIgnoringCase("Jim");
    }

}