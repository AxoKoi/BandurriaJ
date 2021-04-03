package com.axokoi.bandurriaj.views;

import com.axokoi.bandurriaj.controllers.MenuBarController;
import com.axokoi.bandurriaj.i18n.MessagesProvider;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class MenuBarView extends MenuBar {

    private final MenuBarController menuBarController;
    private final MessagesProvider messagesProvider;
    public MenuBarView(MenuBarController menuBarController, MessagesProvider messagesProvider) {
        this.menuBarController = menuBarController;
        this.messagesProvider = messagesProvider;
    }

    public void build(Stage stage) {
        Menu menu1 = getMenu1();
        Menu menu2 = getMenu2(stage);
        Menu menu3 = getMenu3();

        this.getMenus().add(menu1);
        this.getMenus().add(menu2);
        this.getMenus().add(menu3);
    }


    private Menu getMenu1() {
        Menu menu1 = new Menu(messagesProvider.getMessageFrom("menubar.view.file"));
        MenuItem menuItem1 = new MenuItem(messagesProvider.getMessageFrom("menubar.view.save"));
        menu1.getItems().add(menuItem1);
        return menu1;
    }

    private Menu getMenu2(Stage stage) {
        Menu menu2 = new Menu(messagesProvider.getMessageFrom("menubar.view.import"));
        MenuItem menu2Item1 = new MenuItem(messagesProvider.getMessageFrom("menubar.view.import.from.cd"));

        DirectoryChooser directoryChooser = new DirectoryChooser();

        menu2Item1.setOnAction(e -> {
            File selectedFile = directoryChooser.showDialog(stage);
            menuBarController.handleReadCd(selectedFile);

        });
        menu2.getItems().add(menu2Item1);
        return menu2;
    }

    private Menu getMenu3() {
        Menu menu3 = new Menu(messagesProvider.getMessageFrom("menubar.view.settings"));
        Menu menu3_1 = new Menu(messagesProvider.getMessageFrom("menubar.view.settings.language"));

        menu3.getItems().add(menu3_1);

        MenuItem menu3Item1 = new MenuItem("EN");
        menu3Item1.setOnAction(event -> menuBarController.changeLocale("EN"));

        MenuItem menu3Item2 = new MenuItem("FR");
        menu3Item2.setOnAction(event -> menuBarController.changeLocale("FR"));

        menu3_1.getItems().addAll(menu3Item1, menu3Item2);
        return menu3;
    }


}