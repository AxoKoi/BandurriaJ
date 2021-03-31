package com.axokoi.bandurriaj.model;

import javax.persistence.*;


@Entity
public class Artist implements Searchable {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;
   private String name;
   private String role;
   @Lob
   private String comment;
   private String mbIdentifier;
   private Artist.Type type;

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   @Override
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getRole() {
      return role;
   }

   public void setRole(String role) {
      this.role = role;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   public String getMbIdentifier() {
      return mbIdentifier;
   }

   public void setMbIdentifier(String mbIdentifier) {
      this.mbIdentifier = mbIdentifier;
   }

   public enum Type {
      COMPOSITE, SINGLE, UNKNOWN;
   }
}

