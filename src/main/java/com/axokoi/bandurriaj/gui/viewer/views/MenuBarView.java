package com.axokoi.bandurriaj.gui.viewer.views;

import com.axokoi.bandurriaj.gui.viewer.controllers.MenuBarController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Optional;

@Component
public class MenuBarView extends MenuBar {

    private final MenuBarController menuBarController;
    private final MessagesProvider messagesProvider;

    public MenuBarView(MenuBarController menuBarController, MessagesProvider messagesProvider) {
        this.menuBarController = menuBarController;
        this.messagesProvider = messagesProvider;
    }

    public void build(Stage stage) {
        Menu filesMenu = getMenuFiles();
        Menu importMenu = getMenuImport(stage);
        Menu settingsMenu = getMenuSettings(stage);

        this.getMenus().add(filesMenu);
        this.getMenus().add(importMenu);
        this.getMenus().add(settingsMenu);
    }


    private Menu getMenuFiles() {
        Menu menu = new Menu(messagesProvider.getMessageFrom("menubar.view.file"));
        MenuItem menuItem1 = new MenuItem(messagesProvider.getMessageFrom("menubar.view.save"));
        menu.getItems().add(menuItem1);
        return menu;
    }

    private Menu getMenuImport(Stage stage) {
        Menu menu = new Menu(messagesProvider.getMessageFrom("menubar.view.import"));
        MenuItem menuItem = new MenuItem(messagesProvider.getMessageFrom("menubar.view.import.from.cd"));

        menuItem.setOnAction(e -> {
            Optional<String> userPreferredPath = menuBarController.getUserPreferredPath();
            File pathToDriver = userPreferredPath.map(File::new).orElseGet(() -> choosePathToPreferredDriver(stage));
            menuBarController.handleReadCd(pathToDriver);
        });

        menu.getItems().add(menuItem);
        return menu;
    }

    private File choosePathToPreferredDriver(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File path = directoryChooser.showDialog(stage);
        menuBarController.savePathToPreferredDriver(path.getAbsolutePath());
        return path;
    }

    private Menu getMenuSettings(Stage stage) {
        Menu menu = new Menu(messagesProvider.getMessageFrom("menubar.view.settings"));
        Menu menu1 = new Menu(messagesProvider.getMessageFrom("menubar.view.settings.language"));
        createLanguageMenu(menu1);
        menu.getItems().add(menu1);

        Menu menu2 = new Menu(messagesProvider.getMessageFrom("menubar.view.settings.change.path.to.driver"));
        menu2.setOnAction(event->this.choosePathToPreferredDriver(stage));
        menu.getItems().add(menu2);

        return menu;
    }

    private void createLanguageMenu(Menu menu) {
        MenuItem menu3Item1 = new MenuItem("English");
        menu3Item1.setOnAction(event -> menuBarController.changeLocale("EN"));

        MenuItem menu3Item2 = new MenuItem("Français");
        menu3Item2.setOnAction(event -> menuBarController.changeLocale("FR"));

        MenuItem menu3Item3 = new MenuItem("Español");
        menu3Item3.setOnAction(event -> menuBarController.changeLocale("ES"));

        menu.getItems().addAll(menu3Item1, menu3Item2,menu3Item3);
    }

}