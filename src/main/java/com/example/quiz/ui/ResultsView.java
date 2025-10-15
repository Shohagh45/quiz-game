package com.example.quiz.ui;

import com.example.quiz.Router;
import com.example.quiz.io.QuizIO;
import com.example.quiz.state.GameManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ResultsView {
    private final Router router;
    private final Scene scene;

    public ResultsView(Router router) {
        this.router = router;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        Label header = new Label("Results");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        GameManager gm = GameManager.getInstance();
        int total = gm.getTotalQuestions();
        int correct = gm.scoreProperty().get();

        Label summary = new Label(gm.playerNameProperty().get() + ", you scored " + correct + " / " + total);

        Button saveBtn = new Button("Save Highscore");
        Button menuBtn = new Button("Back to Menu");

        VBox center = new VBox(10, summary, saveBtn, menuBtn);
        center.setPadding(new Insets(10));
        root.setTop(header);
        root.setCenter(center);

        saveBtn.setOnAction(e -> {
            try {
                QuizIO.saveResult(gm.getQuiz(),
                        gm.playerNameProperty().get(),
                        total, correct);
                alert("Saved", "Result written to results/" + gm.getQuiz().getQuizId() + "-results.json");
            } catch (IOException ex) {
                error("Save failed", ex.getMessage());
            }
        });

        menuBtn.setOnAction(e -> router.showMenu());

        this.scene = new Scene(root, 760, 520);
    }

    private void alert(String header, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Info");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
    private void error(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    public Scene getScene() { return scene; }
}
