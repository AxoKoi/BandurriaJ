package com.axokoi.bandurriaj.gui.viewer.views;

import com.axokoi.bandurriaj.gui.viewer.controllers.LoadedCdController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.model.Catalogue;
import com.axokoi.bandurriaj.model.Disc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoadedCdView extends VBox {


    private final String WARNING;
    private final Label warningLabel;
    private final Label cdName;
    private final ComboBox<Disc> cds = new ComboBox<>();
    private final Label catalogueName;
    private final ComboBox<Catalogue> catalogues = new ComboBox<>();
    private final Button cancelButton;
    private final Button saveButton;
    private final ButtonBar cancelSaveBar = new ButtonBar();

    @Autowired
    private LoadedCdController controller;

    private final MessagesProvider messagesProvider;

    public LoadedCdView(MessagesProvider messagesProvider) {
        this.messagesProvider = messagesProvider;

        WARNING = this.messagesProvider.getMessageFrom("loadedCD.view.warning");
        warningLabel = new Label(WARNING);
        warningLabel.setVisible(false);

        cdName  = new Label(this.messagesProvider.getMessageFrom("loadedCD.view.your.cd.is"));
        catalogueName= new Label(this.messagesProvider.getMessageFrom("loadedCD.view.choose.catalogue"));
        cancelButton = new Button(this.messagesProvider.getMessageFrom("button.cancel"));
        saveButton = new Button(this.messagesProvider.getMessageFrom("button.save"));

        ButtonBar.setButtonData(cancelButton, ButtonBar.ButtonData.NO);
        ButtonBar.setButtonData(saveButton, ButtonBar.ButtonData.YES);
        cancelSaveBar.getButtons().addAll(cancelButton, saveButton);
        this.getChildren().addAll(warningLabel, cdName, cds, catalogueName, catalogues, cancelSaveBar);
        cancelButton.setOnAction(getCancelHandler());
        saveButton.setOnAction(getSaveHandler());
    }

    private EventHandler<ActionEvent> getSaveHandler() {
        return e -> {
            Disc selectedCd = cds.getSelectionModel().getSelectedItem();

            selectedCd = controller.saveCdOnCatalogue(selectedCd,
                    catalogues.getSelectionModel().getSelectedItem());

            controller.displayCd(selectedCd);
            controller.dispatchRefreshToCatalogue();
            ((Stage) saveButton.getScene().getWindow()).close();
        };
    }

    private EventHandler<ActionEvent> getCancelHandler() {
        return e -> ((Stage) cancelButton.getScene().getWindow()).close();
    }

    public void refresh(List<Disc> loadedCds) {

        //todo handle if loadedCds is empty!
        if (loadedCds.size() > 1) {
            warningLabel.setVisible(true);
        }
        List<Catalogue> existingCatalogues = controller.getCatalogues();
        catalogues.getItems().clear();
        catalogues.getItems().addAll(existingCatalogues);
        catalogues.setCellFactory(x -> new CatalogueCell());
        //todo can we avoid or refactor these converters. They are needed to display the
        // name of the catalogue in the selected box.
        catalogues.setConverter(new StringConverter<>() {

            @Override
            public String toString(Catalogue catalogue) {
                if (catalogue == null) {
                    return "";
                }
                return catalogue.getName();
            }

            @Override
            public Catalogue fromString(String s) {
                return null;
            }
        });

        cds.getItems().clear();
        cds.getItems().addAll(loadedCds);
        cds.setCellFactory(x -> new CdsCell());
        cds.setConverter(new StringConverter<>() {

            @Override
            public String toString(Disc disc) {
                if (disc == null) {
                    return "";
                }
                return disc.getName();
            }

            @Override
            public Disc fromString(String s) {
                return null;
            }
        });
        Stage popUpStage = new Stage();
        if (this.getScene() == null) {
            new Scene(this, 500, 300);
        }
        popUpStage.setScene(this.getScene());
        popUpStage.show();

    }

    //todo we can extracts these cells into a generic one
    static class CatalogueCell extends ListCell<Catalogue> {
        @Override
        protected void updateItem(Catalogue catalogue, boolean empty) {
            super.updateItem(catalogue, empty);
            setText(catalogue == null ? "" : catalogue.getName());
        }
    }

    static class CdsCell extends ListCell<Disc> {
        @Override
        protected void updateItem(Disc disc, boolean empty) {
            super.updateItem(disc, empty);
            setText(disc == null ? "" : disc.getName());
        }
    }
}
