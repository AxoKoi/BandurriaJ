package com.axokoi.bandurriaj.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Disc implements Searchable {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

   private String name;

   @ManyToMany(targetEntity = Artist.class, fetch = FetchType.EAGER)
   private List<Artist> artists;

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

   public List<Artist> getArtists() {
      return artists;
   }

   public void setArtists(List<Artist> artists) {
      this.artists = artists;
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
