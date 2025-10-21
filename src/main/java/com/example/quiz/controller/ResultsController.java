package com.example.quiz.controller;

import com.example.quiz.Router;
import com.example.quiz.view.ResultsView;

public class ResultsController {
    private final Router router;
    private final ResultsView view;

    public ResultsController(Router router, ResultsView view) {
        this.router = router;
        this.view = view;
        view.menuBtn().setOnAction(e -> router.showMenu());
        // Data population will be added in Commit 2
    }
}
