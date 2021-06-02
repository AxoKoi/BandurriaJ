package com.axokoi.bandurriaj.gui.editor.controllers;

import com.axokoi.bandurriaj.gui.editor.views.AddTrackEditorView;
import com.axokoi.bandurriaj.model.Track;
import com.axokoi.bandurriaj.services.dataaccess.TrackService;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AddTrackEditorController extends EditorController<Track> {

   private final TrackService trackService;

   @Autowired
   private AddTrackEditorView view;

   public AddTrackEditorController(TrackService trackService) {
      this.trackService = trackService;
   }


   @Override
   protected AddTrackEditorView getView() {
      return view;
   }

   @Override
   protected void refreshView(Track searchable) {
      Assert.isNull(searchable, "When adding, the track to refresh the view with should be null!");
      view.refresh(new Track());
   }

   public void refreshView() {
      this.refreshView(null);
   }

   @Override
   public void onCancel(ActionEvent event) {

   }

   @Override
   public void onSave(ActionEvent event) {
      trackService.save(view.getEntityToEdit());
   }
}
