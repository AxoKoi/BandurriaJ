package com.axokoi.bandurriaj.services.cdreader;

import com.axokoi.bandurriaj.model.Track;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class ToCStringToListOfTracksConverterTest {

   ToCStringToListOfTracksConverter converter = new ToCStringToListOfTracksConverter();

   @Test
   void converter() {
      List<Track> tracks = converter.convert("1 10 185955 183 10280 28558 39600 66103 93568 103533 111993 154713 175170");
      Assertions.assertThat(tracks.size()).isEqualTo(10);
      Assertions.assertThat(tracks.get(3).getNumber()).isEqualTo(4);
      Assertions.assertThat(tracks.get(6).getDuration()).isEqualTo("1:52");

   }

   @Test
   void isValid() {
      Assertions.assertThat(converter.isValid("1 10 185955 183 10280 28558 39600 66103 93568 103533 111993 154713 175170")).isTrue();
   }

   @Test
   void isNotValid() {
      Assertions.assertThat(converter.isValid("1 11 185955 183 10280 28558 39600 66103 93568 103533 111993 154713 175170")).isFalse();
      Assertions.assertThat(converter.isValid("1 10 185955x 183 10280 28558 39600 66103 93568 103533 111993 154713 175170")).isFalse();
   }
}