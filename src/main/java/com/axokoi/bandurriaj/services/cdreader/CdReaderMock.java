package com.axokoi.bandurriaj.services.cdreader;

import org.springframework.context.annotation.Profile;

import java.util.UUID;

@Profile("isolated")
public class CdReaderMock implements CdReader {

   @Override
   public String readId(String driverPath) {
      return UUID.randomUUID().toString();
   }
}
