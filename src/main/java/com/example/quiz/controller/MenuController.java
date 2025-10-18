package com.example.quiz.controller;

import com.example.quiz.Router;
import com.example.quiz.io.QuizIO;
import com.example.quiz.model.Quiz;
import com.example.quiz.state.GameManager;
import com.example.quiz.view.MenuView;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

public class MenuController {
    private final Router router;
    private final MenuView view;
    private final GameManager gm = GameManager.getInstance();

    public MenuController(Router router, MenuView view) {
        this.router = router;
        this.view = view;
        hookEvents();
    }

    private void hookEvents() {
        view.getLoadBtn().setOnAction(e -> onLoadQuiz());
        view.getStartBtn().setOnAction(e -> router.showGame());
        view.getStartBtn().setDisable(true);
    }

    private void onLoadQuiz() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Quiz JSON");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File f = chooser.showOpenDialog(view.getScene().getWindow());
        if (f == null) return;
        try {
            Quiz quiz = QuizIO.loadQuiz(f);
            gm.loadQuiz(quiz);
            view.getDescArea().setText(quiz.getDescription() == null ? "" : quiz.getDescription());
            view.getStatusLabel().setText("Loaded: " + quiz.getTitle() + " (" + gm.getTotalQuestions() + " questions)");
            view.getStartBtn().setDisable(false);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Failed to load quiz");
            a.setContentText(ex.getMessage());
            a.showAndWait();
        }
    }
}
