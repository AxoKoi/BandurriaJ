package com.axokoi.bandurriaj.services.cdreader;

import com.axokoi.bandurriaj.model.Track;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class ToCStringToListOfTracksConverter {

   public List<Track> convert(String toC) {
      if (!isValid(toC)) {
         throw new RuntimeException("Wrong format of ToC:" + toC);
      }
      String[] entries = toEntries(toC);
      String[] durations = toDuration(entries);
      int firstTrackNumber = Integer.parseInt(entries[0]);
      int numberOfTracks = Integer.parseInt(entries[1]);
      List<Track> tracks = new LinkedList<>();

      for (int i = 0; i < numberOfTracks; i++) {
         Track track = new Track();
         track.setDuration(durations[i]);
         track.setNumber((firstTrackNumber + i));
         track.setName("Track" + (firstTrackNumber + i));
         tracks.add(track);
      }
      return tracks;
   }

   private String[] toDuration(String[] entries) {
      int[] durationInSectors = toDurationInSectors(entries);
      String[] durationAsFormat = new String[durationInSectors.length];

      for (int i = 0; i < durationInSectors.length; i++) {
         durationAsFormat[i] = secondsToFormattedDuration(durationInSectors[i] * 1.0 / 75);
      }

      return durationAsFormat;
   }

   private String secondsToFormattedDuration(double time) {
      int minutes = (int) (time / (60 ));
      int seconds = (int) ((time) % 60);
      return String.format("%d:%02d", minutes, seconds);

   }


   private int[] toDurationInSectors(String[] entries) {

      int totalLengthInSectors = Integer.parseInt(entries[2]);
      String[] tracksOffSet = Arrays.copyOfRange(entries, 3, entries.length);
      int numberOfTracks = Integer.parseInt(entries[1]);

      int[] durations = new int[numberOfTracks];
      for (int i = 0; i < numberOfTracks - 1; i++) {
         durations[i] = Integer.parseInt(tracksOffSet[i + 1]) - Integer.parseInt(tracksOffSet[i]);
      }
      durations[numberOfTracks - 1] = totalLengthInSectors - Integer.parseInt(tracksOffSet[numberOfTracks - 1]);
      return durations;
   }

   //75 sectors per seconds
   //Sample result
   //1 10 185955 183 10280 28558 39600 66103 93568 103533 111993 154713 175170
   public boolean isValid(String toC) {
      String[] entries = toEntries(toC);
      if (entries.length != Integer.parseInt(entries[1]) + 3) {
         return false;
      }
      try {
         Arrays.stream(entries).sequential().forEach((Integer::parseInt));
      } catch (Exception e) {
         return false;
      }
      return true;
   }

   private String[] toEntries(String toC) {
      return toC.trim().split(String.valueOf(' '));
   }
}
