package com.axokoi.bandurriaj.services.tagging.musicbrainz;

import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.musicbrainz.MBWS2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
class CdQueryTest {

    @Autowired
    private CdQueryImpl cdQueryImpl;

    //Non unitary test, as it's really trying to connect to the API.
    // We use it mainly for development purpose.
    // you can supress it.
    @Test
    void findCd() {

        String id = ".p4ZJ206p8mpaTvnG8.ZG9_qagE-";
        Disc disc = cdQueryImpl.getDiscInfoById(id).get(0);
        assertThat(disc).isNotNull();


    }
}