package com.example.quiz.controller;

import com.example.quiz.Router;
import com.example.quiz.view.GameView;

public class GameController {
    private final Router router;
    private final GameView view;

    public GameController(Router router, GameView view) {
        this.router = router;
        this.view = view;

    }
}
