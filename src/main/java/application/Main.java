package application;

import application.configuration.Config;
import application.userInterface.MenuApp;

public class Main {

    public static void main(String[] args) {
        MenuApp menuApp = Config.createMenuApp();
        menuApp.showMainMenu();
    }
}
