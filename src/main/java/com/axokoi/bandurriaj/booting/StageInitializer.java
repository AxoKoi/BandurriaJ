package com.axokoi.bandurriaj.booting;

import com.axokoi.bandurriaj.MainApplication;
import com.axokoi.bandurriaj.ViewDispatcher;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import com.axokoi.bandurriaj.gui.viewer.views.CatalogueView;
import com.axokoi.bandurriaj.gui.viewer.views.MenuBarView;
import com.axokoi.bandurriaj.gui.viewer.views.SmartSearchView;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class StageInitializer implements ApplicationListener<MainApplication.StageReadyEvent> {

    private final CatalogueView catalogueView;

    private final SmartSearchView smartSearchView;

    private final ViewDispatcher viewDispatcher;

    private final MenuBarView menuBarView;

    private final MessagesProvider messagesProvider;

    public StageInitializer(CatalogueView catalogueView, SmartSearchView smartSearchView, ViewDispatcher viewDispatcher, MenuBarView menuBarView, MessagesProvider messagesProvider) {
        this.catalogueView = catalogueView;
        this.smartSearchView = smartSearchView;
        this.viewDispatcher = viewDispatcher;
        this.menuBarView = menuBarView;
        this.messagesProvider = messagesProvider;
    }

    @Override
    public void onApplicationEvent(MainApplication.StageReadyEvent event) {

        catalogueView.refresh();
        catalogueView.setPrefHeight(5);

        Stage stage = event.getStage();
        BorderPane mainPane = new BorderPane();
        menuBarView.build(stage);

        VBox center = new VBox();
        center.setPadding(new Insets(5));
        mainPane.setTop(menuBarView);
        mainPane.setLeft(catalogueView);
        mainPane.setCenter(center);
        mainPane.setRight(smartSearchView);

        HBox footerView = new HBox();
        footerView.getChildren().add(new Text(messagesProvider.getMessageFrom("footer.copyright")));
        mainPane.setBottom(footerView);

        stage.setTitle(messagesProvider.getMessageFrom("app.title"));

        stage.setScene(new Scene(mainPane));
        stage.sizeToScene();
        new JMetro(stage.getScene(), Style.LIGHT);

        stage.getScene().getStylesheets().clear();
        stage.getScene().getStylesheets().add("styles.css");
        viewDispatcher.setBorderPane(mainPane);

        stage.show();
        stage.setMaximized(true);

    }
}
