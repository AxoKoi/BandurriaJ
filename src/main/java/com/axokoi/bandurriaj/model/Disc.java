package com.axokoi.bandurriaj.model;

import java.util.List;

import javax.persistence.*;

@Entity
public class Disc implements Searchable {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String name;

   @ManyToOne(targetEntity = Band.class)
   private Band band;

   @OneToMany(targetEntity = Track.class, cascade = CascadeType.ALL)
   private List<Track> tracks;

   @Lob
   private String comment;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Band getBand() {
      return band;
   }

   public void setBand(Band band) {
      this.band = band;
   }

   public List<Track> getTracks() {
      return tracks;
   }

   public void setTracks(List<Track> tracks) {
      this.tracks = tracks;
   }

   public String getComment() {
      return comment;
   }

   public void setComment(String comment) {
      this.comment = comment;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }
}
