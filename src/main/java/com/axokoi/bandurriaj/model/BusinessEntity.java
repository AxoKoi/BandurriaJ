package com.axokoi.bandurriaj.model;

import java.util.UUID;

public abstract class BusinessEntity<T> {

   private final String businessIdentifier;


   protected BusinessEntity() {
      this.businessIdentifier = UUID.randomUUID().toString();
   }

   protected String getBusinessIdentifier() {
      return businessIdentifier;
   }
}
