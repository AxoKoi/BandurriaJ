package com.axokoi.bandurriaj.gui.commons.handlers.key;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * A class to wrap a handler responding to a enter key event.
 */
public class EnterKeyHandler implements EventHandler<KeyEvent> {
   private final EventHandler<KeyEvent> handler;

   /**
    * @param handler that will be executed if the event was a enter key pushed
    */
   public EnterKeyHandler(EventHandler<KeyEvent> handler) {
      this.handler = handler;
   }

   @Override
   public void handle(KeyEvent keyEvent) {
      if (keyEvent.getCode().equals(KeyCode.ENTER)) {
         handler.handle(keyEvent);
      }
   }
}