package com.example.quiz;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Router router = new Router(stage);
        router.showMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
