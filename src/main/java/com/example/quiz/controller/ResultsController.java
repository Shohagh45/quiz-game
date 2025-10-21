package com.example.quiz.controller;

import com.example.quiz.Router;
import com.example.quiz.io.QuizIO;
import com.example.quiz.model.Quiz;
import com.example.quiz.state.GameManager;
import com.example.quiz.view.ResultsView;
import com.example.quiz.view.ResultsView.ResultRow;
import javafx.scene.control.Alert;

import java.util.List;

public class ResultsController {
    private final Router router;
    private final ResultsView view;
    private final GameManager gm = GameManager.getInstance();

    public ResultsController(Router router, ResultsView view) {
        this.router = router;
        this.view = view;
        init();
    }

    private void init() {
        try {
            Quiz quiz = gm.getQuiz();
            int total   = gm.getTotalQuestions();
            int correct = gm.getScore();

            String player = gm.playerNameProperty().get();

            QuizIO.appendResult(quiz, player, total, correct);
            List<ResultRow> rows = QuizIO.readResults(quiz);
            view.table().getItems().setAll(rows);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Results");
            a.setHeaderText("Failed to save/load results");
            a.setContentText(ex.getMessage());
            a.showAndWait();
        }

        view.menuBtn().setOnAction(e -> router.showMenu());
    }
}
