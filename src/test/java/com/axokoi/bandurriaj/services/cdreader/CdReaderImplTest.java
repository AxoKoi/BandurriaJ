package com.axokoi.bandurriaj.services.cdreader;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A test class to work that the LibdiscId is working.
 * It's not a unit test, used as a development tool.
 */
@Ignore
@RunWith(SpringRunner.class)
public class CdReaderImplTest {
   /**
    * You can use here the relative path to the libdiscId that you're using,
    * without the extension!
    */
   String pathToLib = "/lib/libdiscid/win64/discid";
   String DRIVER_PATH = "D:";
   ToCStringToListOfTracksConverter converter = new ToCStringToListOfTracksConverter();

   @Test
   public void readId() {
      CdReader cdReader = new CdReaderImpl(pathToLib);
      String cdId = cdReader.readId(DRIVER_PATH);
      assertThat(cdId).isNotNull().isNotEmpty();
   }


   @Test
   public void readToC(){
      CdReader cdReader = new CdReaderImpl(pathToLib);
      String ToC = cdReader.readToC(DRIVER_PATH);
      assertThat(ToC).isNotNull().isNotEmpty();
      assertThat(converter.isValid(ToC)).isTrue();
   //1 10 185955 183 10280 28558 39600 66103 93568 103533 111993 154713 175170
   }
}