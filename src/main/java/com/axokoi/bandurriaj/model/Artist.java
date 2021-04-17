package com.axokoi.bandurriaj.model;

import javax.persistence.*;
import java.util.Objects;


@Entity
public class Artist extends BusinessEntity<Artist> implements Searchable {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;
   @Column(unique = true, updatable = false)
   private final String businessIdentifier;

   private String name;
   private String role;
   @Lob
   private String comment;
   private String mbIdentifier;
   private Artist.Type type;

   public Artist() {
      businessIdentifier = super.getBusinessIdentifier();
   }

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

   @Override
   public String getBusinessIdentifier() {
      return businessIdentifier;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Artist artist = (Artist) o;
      return businessIdentifier.equals(artist.businessIdentifier);
   }

   @Override
   public int hashCode() {
      return Objects.hash(businessIdentifier);
   }

   public enum Type {
      COMPOSITE, SINGLE, UNKNOWN
   }
}

