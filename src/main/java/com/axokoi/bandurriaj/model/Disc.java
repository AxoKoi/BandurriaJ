package com.axokoi.bandurriaj.model;

import javax.persistence.*;
import java.util.*;

@Entity
public class Disc extends BusinessEntity<Disc> implements Searchable {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long id;

   /**
    * The business identifier used to compare two discs.
    */
   @Column(unique = true, updatable = false)
   private final String businessIdentifier;

   private String name;

   @ManyToMany(targetEntity = Artist.class, fetch = FetchType.EAGER)
   private Set<Artist> creditedArtists;

   @ManyToMany(targetEntity = Artist.class, fetch = FetchType.EAGER)
   private Set<Artist> relatedArtist;

   @OneToMany(targetEntity = Track.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
   private Set<Track> tracks;

   /**
    * Additional identifiers for different use.
    */
   @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
   private Set<ExternalIdentifier> externalIdentifier;

   @Column(nullable = false)
   private String discId;

   @Lob
   private String comment;

   private String pathToImage;

   @OneToMany(fetch = FetchType.EAGER)
   private Set<MusicGenre> genres;

   public Disc() {
      businessIdentifier = super.getBusinessIdentifier();
   }

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
      if (this.getCreditedArtists() != null) {
         allArtist.addAll(this.getCreditedArtists());
      }
      if (this.getRelatedArtist() != null) {
         allArtist.addAll(this.getRelatedArtist());
      }
      return allArtist;
   }

   public Set<ExternalIdentifier> getExternalIdentifiers() {
      if (externalIdentifier == null) {
         externalIdentifier = new HashSet<>();
      }
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

   @Override
   public String getBusinessIdentifier() {
      return businessIdentifier;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Disc disc = (Disc) o;
      return businessIdentifier.equals(disc.businessIdentifier);
   }

   @Override
   public int hashCode() {
      return Objects.hash(businessIdentifier);
   }

   public String getDiscId() {
      return discId;
   }

   public void setDiscId(String discId) {
      this.discId = discId;
   }

   public Optional<String> getUserIdentifier() {
      if (this.getExternalIdentifiers() == null) {
         return Optional.empty();
      }
      return getExternalIdentifiers().stream()
              .filter(x -> x.getType() == ExternalIdentifier.Type.USER)
              .map(ExternalIdentifier::getIdentifier)
              .findAny();
   }

   public void addExternalIdentifier(ExternalIdentifier userExternalIdentifier) {
      if (this.getExternalIdentifiers() == null) {
         this.externalIdentifier = new HashSet<>();
      }
      externalIdentifier.add(userExternalIdentifier);
   }

   public Set<MusicGenre> getGenres() {
      if (genres == null) {
         return new HashSet<>();
      }
      return genres;
   }

   public void setGenres(Set<MusicGenre> genres) {
      this.genres = genres;
   }
}
