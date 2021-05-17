package com.axokoi.bandurriaj.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MusicGenre implements Searchable {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;

   @Column(unique = true, updatable = false)
   private String name;

   public MusicGenre(){}

   public MusicGenre(String name) {
      this.name = name;
   }


   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MusicGenre that = (MusicGenre) o;
      return name.equals(that.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public String getName() {
      return name;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }
}
