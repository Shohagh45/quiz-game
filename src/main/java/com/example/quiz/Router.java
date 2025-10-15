package com.example.quiz;

import com.example.quiz.ui.GameView;
import com.example.quiz.ui.MenuView;
import com.example.quiz.ui.ResultsView;
import javafx.stage.Stage;

public class Router {
    private final Stage stage;

    public Router(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("JavaFX Quiz Game");
    }

    public void showMenu() {
        stage.setScene(new MenuView(this).getScene());
        stage.show();
    }

    public void showGame() {
        stage.setScene(new GameView(this).getScene());
        stage.show();
    }

    public void showResults() {
        stage.setScene(new ResultsView(this).getScene());
        stage.show();
    }
}
