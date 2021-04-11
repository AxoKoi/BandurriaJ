package com.axokoi.bandurriaj.model;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Disc implements Searchable {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;

   private String name;

   @ManyToMany(targetEntity = Artist.class, fetch = FetchType.EAGER)
   private Set<Artist> creditedArtists;

   @ManyToMany(targetEntity = Artist.class, fetch = FetchType.EAGER)
   private Set<Artist> relatedArtist;

   @OneToMany(targetEntity = Track.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   private Set<Track> tracks;

   @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<ExternalIdentifier>  externalIdentifier;

   @Lob
   private String comment;

   private String pathToImage;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public Set<Artist> getCreditedArtists() {
      return creditedArtists;
   }
   public Set<Artist> getRelatedArtist() {
      return relatedArtist;
   }

   public void setRelatedArtist(Set<Artist> relatedArtist) {
      this.relatedArtist = relatedArtist;
   }
   public void setCreditedArtists(Set<Artist> artists) {
      this.creditedArtists = artists;
   }

   public Set<Track> getTracks() {
      return tracks;
   }

   public void setTracks(Set<Track> tracks) {
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

   public Set<Artist> getAllArtist() {
      Set<Artist> allArtist = new LinkedHashSet<>();
      allArtist.addAll(this.getCreditedArtists());
      allArtist.addAll(this.getRelatedArtist());
      return allArtist;
   }

   public Set<ExternalIdentifier> getExternalIdentifier() {
      return externalIdentifier;
   }

   public void setExternalIdentifier(Set<ExternalIdentifier> externalIdentifier) {
      this.externalIdentifier = externalIdentifier;
   }

   public String getPathToImage() {
      return pathToImage;
   }

   public void setPathToImage(String pathToImage) {
      this.pathToImage = pathToImage;
   }
}
