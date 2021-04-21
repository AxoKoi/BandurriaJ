package com.axokoi.bandurriaj.gui.commons.handlers.mouse;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * A class to wrap a handler responding to a double click event.
 */
public class DoubleClickHandler implements EventHandler<MouseEvent> {
   private final EventHandler<MouseEvent> handler;

   /**
    *
    * @param handler that will be executed if the event was a double click of the Primary Button.
    */
   public DoubleClickHandler(EventHandler<MouseEvent> handler) {
      this.handler = handler;
   }

   @Override
   public void handle(MouseEvent mouseEvent) {
      if (mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2) {
         handler.handle(mouseEvent);
      }
   }
}