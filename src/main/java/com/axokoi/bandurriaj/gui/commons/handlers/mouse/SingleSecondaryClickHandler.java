package com.axokoi.bandurriaj.gui.commons.handlers.mouse;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class SingleSecondaryClickHandler implements EventHandler<MouseEvent> {
   private final EventHandler<MouseEvent> handler;

   /**
    *
    * @param handler that will be executed if the event was a single click of the Secondary Button.
    */
   public SingleSecondaryClickHandler(EventHandler<MouseEvent> handler) {
      this.handler = handler;
   }

   @Override
   public void handle(MouseEvent mouseEvent) {
      if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 1) {
         handler.handle(mouseEvent);
      }
   }
}