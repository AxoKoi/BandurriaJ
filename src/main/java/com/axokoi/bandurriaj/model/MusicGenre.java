package com.axokoi.bandurriaj.model;

import javax.persistence.*;

@Entity
public class MusicGenre implements Searchable {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;

   @Column(unique = true, updatable = false)
   private String name;

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
