package com.axokoi.bandurriaj.gui.commons.popups;

import com.axokoi.bandurriaj.gui.commons.GuiController;
import com.axokoi.bandurriaj.model.Disc;
import com.axokoi.bandurriaj.services.dataaccess.DiscService;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteDiscWarningPopupController extends GuiController<Disc> {

   @Autowired
   private  DeleteDiscWarningPopupView deleteDiscWarningPopupView;

   private final DiscService discService;

   public DeleteDiscWarningPopupController( DiscService discService, DiscService discService1) {
      this.discService = discService1;
   }

   @Override
   public DeleteDiscWarningPopupView getView() {
      return deleteDiscWarningPopupView;
   }

   @Override
   public void refreshView(Disc disc) {
    deleteDiscWarningPopupView.refreshView(disc);
   }

   public void onOk(ActionEvent actionEvent) {
      discService.deleteDisc(deleteDiscWarningPopupView.getEntityToDelete());
   }
}
