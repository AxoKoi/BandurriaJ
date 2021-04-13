package com.axokoi.bandurriaj.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ExternalIdentifier {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;
   private String identifier;
   private Type type;

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   public enum Type {
      MUSICBRAINZ
   }


   public void setId(Long id) {
      this.id = id;
   }

   public Long getId() {
      return id;
   }

   public String getIdentifier() {
      return identifier;
   }

   public void setIdentifier(String identifier) {
      this.identifier = identifier;
   }
}
