package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CdQueryTest {

    //Non unitary test, as it's really trying to connect to the API.
    // We use it mainly for development purpose.
    @Test
    void findCd() {
        Disc disc = CdQuery.getDiscInfo("Morrison Hotel");
        assertThat(disc).isNotNull();
    }
}