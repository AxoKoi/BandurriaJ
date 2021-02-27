package com.axokoi.bandurriaj.services.cdreader;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Profile("isolated")
@Component
public class CdReaderMock implements CdReader {

   @Override
   public String readId(String driverPath) {
      return UUID.randomUUID().toString();
   }
}
