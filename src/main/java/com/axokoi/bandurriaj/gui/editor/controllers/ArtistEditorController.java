package com.axokoi.bandurriaj.gui.editor.controllers;

import com.axokoi.bandurriaj.gui.editor.views.ArtistEditorView;
import com.axokoi.bandurriaj.gui.viewer.controllers.ArtistController;
import com.axokoi.bandurriaj.model.Artist;
import com.axokoi.bandurriaj.services.dataaccess.ArtistService;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArtistEditorController extends EditorController<Artist> {

   @Autowired
   private ArtistEditorView artistEditorView;
   private final ArtistService artistService;

   @Autowired
   private  ArtistController artistController;

   public ArtistEditorController(ArtistService artistService) {
      this.artistService = artistService;
   }

   @Override
   public ArtistEditorView getView() {
      return artistEditorView;
   }

   @Override
   public void refreshView(Artist artist) {
      artistEditorView.refresh(artist);
   }

   @Override
   public void onCancel(ActionEvent event) {

   }

   @Override
   public void onSave(ActionEvent event) {
      Artist artistToEdit = artistEditorView.getEntityToEdit();
      artistService.save(artistToEdit);
      //IRO could it be better to be some kind of callback function given through the popup displayer?
      artistController.refreshView(artistToEdit);
   }
}
