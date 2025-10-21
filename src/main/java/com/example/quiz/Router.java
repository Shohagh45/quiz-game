package com.example.quiz;

import com.example.quiz.controller.GameController;
import com.example.quiz.controller.MenuController;
import com.example.quiz.view.MenuView;
import com.example.quiz.view.GameView;
import com.example.quiz.view.ResultsView;
import javafx.stage.Stage;
import com.example.quiz.controller.ResultsController;
import com.example.quiz.view.ResultsView;

public class Router {
    private final Stage stage;

    public Router(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("JavaFX Quiz Game");
    }

    public void showMenu() {
        MenuView view = new MenuView();
        new MenuController(this, view);
        stage.setScene(view.getScene());
        stage.show();
    }

    public void showGame() {
        GameView view = new GameView();        // no Router in the view anymore
        new GameController(this, view);        // controller owns logic + navigation
        stage.setScene(view.getScene());
        stage.show();
    }

    public void showResults() {
        ResultsView view = new ResultsView();
        new ResultsController(this, view);
        stage.setScene(view.getScene());
        stage.show();
    }
}
