package com.axokoi.bandurriaj.gui.editor.controllers;

import com.axokoi.bandurriaj.gui.editor.views.TrackEditorView;
import com.axokoi.bandurriaj.model.Track;
import com.axokoi.bandurriaj.services.dataaccess.TrackService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;

public class TrackEditorController extends EditorController<Track> {

   @Autowired
   private  TrackEditorView trackEditorView;

   private final TrackService trackService;

   public TrackEditorController(TrackService trackService) {
      this.trackService = trackService;
   }

   @Override
   protected Node getView() {
      return trackEditorView;
   }

   @Override
   protected void refreshView(Track track) {
      trackEditorView.refresh(track);
   }

   @Override
   public void onCancel(ActionEvent event) {

   }

   @Override
   public void onSave(ActionEvent event) {
trackService.save(trackEditorView.getEntityToEdit());
   }
}
