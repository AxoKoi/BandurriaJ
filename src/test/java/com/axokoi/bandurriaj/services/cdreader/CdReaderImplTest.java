package com.axokoi.bandurriaj.services.cdreader;

import com.axokoi.bandurriaj.services.TestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfiguration.class)
class CdReaderImplTest {
    //todo only for linux, we should generify this
    private final static String DRIVE = "/dev/cdrom";

    @Value("${PathToLib}")
    private String pathToLib;

    /*This test is used to check that the library to read the Id from an audio CD
    is working correctly. It's not really a unit test, but it's a nice to have in case
    you want to check that everything is working ok. To configure the test, you
    have to point the library path in the application.properties file.*/
    @Test
    void readId() {
        //CdReader cdReader = new CdReaderImpl(pathToLib);
        //String cdId = cdReader.readId(DRIVE);
       // assertThat(cdId).isNotNull().isNotEmpty();
    }

}
