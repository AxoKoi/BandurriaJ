package com.axokoi.bandurriaj.services.dataaccess;

import com.axokoi.bandurriaj.model.ExternalIdentifier;
import com.axokoi.bandurriaj.model.ExternalIdentifierRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class ExternalIdentifierServiceTest {

   @Mock
   ExternalIdentifierRepository externalIdentifierRepository;

   @InjectMocks
   ExternalIdentifierService externalIdentifierService;

   @Test
   public void getNextUserIdentifier_noUserIdentifierIsPresent() {
      doReturn(Collections.emptyList())
              .when(externalIdentifierRepository)
              .findAllByType(ExternalIdentifier.Type.USER);

      String nextUserIdentifier = externalIdentifierService.getNextUserIdentifier();
      Assert.assertEquals("1", nextUserIdentifier);
   }

   @Test
   public void getNextUserIdentifier_oneUserIdentifierIsPresent() {
      List<ExternalIdentifier> listOfIdentifiers = getExternalIdentifiers();

      doReturn(listOfIdentifiers)
              .when(externalIdentifierRepository)
              .findAllByType(ExternalIdentifier.Type.USER);

      String nextUserIdentifier = externalIdentifierService.getNextUserIdentifier();
      Assert.assertEquals("2", nextUserIdentifier);
   }

   private List<ExternalIdentifier> getExternalIdentifiers() {
      List<ExternalIdentifier> listOfIdentifiers = new ArrayList<>();
      ExternalIdentifier externalIdentifier = new ExternalIdentifier();
      externalIdentifier.setIdentifier("1");
      externalIdentifier.setType(ExternalIdentifier.Type.USER);
      externalIdentifier.setId(123L);
      listOfIdentifiers.add(externalIdentifier);
      return listOfIdentifiers;
   }

}