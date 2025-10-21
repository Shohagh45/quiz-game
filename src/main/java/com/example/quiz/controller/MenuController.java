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
        var owner = (view.getScene() != null) ? view.getScene().getWindow() : null;

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Quiz JSON");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));

        File f = chooser.showOpenDialog(owner);
        if (f == null) return;

        try {
            Quiz quiz = QuizIO.loadQuiz(f);          // throws if bad JSON
            gm.loadQuiz(quiz);                       // reset score/index inside

            // UI feedback
            view.getDescArea().setText(quiz.getDescription() == null ? "" : quiz.getDescription());
            String title = quiz.getTitle() == null ? f.getName() : quiz.getTitle();
            view.getStatusLabel().setText("Loaded: " + title + " (" + gm.getTotalQuestions() + " questions)");
            view.getStartBtn().setDisable(false);
        } catch (Exception ex) {
            ex.printStackTrace(); // helpful in console
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Load error");
            a.setHeaderText("Failed to load quiz file");
            a.setContentText(ex.getMessage());
            a.showAndWait();
            view.getStartBtn().setDisable(true);
            view.getStatusLabel().setText("Load failed.");
        }
    }
}
