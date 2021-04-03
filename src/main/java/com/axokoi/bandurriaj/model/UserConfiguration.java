package com.axokoi.bandurriaj.model;

import javax.persistence.*;

@Entity
public class UserConfiguration {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   @Column(unique = true)
   String key;

   String value;

}
