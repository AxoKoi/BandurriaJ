package com.axokoi.bandurriaj.model;

import javax.persistence.Entity;
import java.util.UUID;

public abstract class BusinessEntity {

   private final String businessIdentifier;


   protected BusinessEntity() {
      this.businessIdentifier = UUID.randomUUID().toString();
   }

   protected String getBusinessIdentifier() {
      return businessIdentifier;
   }
}
