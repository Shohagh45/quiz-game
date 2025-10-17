package com.example.quiz.view;

import com.example.quiz.Router;
import com.example.quiz.io.QuizIO;
import com.example.quiz.model.Quiz;
import com.example.quiz.state.GameManager;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class MenuView {
    private final Router router;
    private final Scene scene;

    public MenuView(Router router) {
        this.router = router;

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        Label title = new Label("JavaFX Quiz Game");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextArea desc = new TextArea();
        desc.setEditable(false);
        desc.setWrapText(true);
        desc.setPromptText("Load a quiz JSON to see its description here...");
        desc.setPrefRowCount(4);

        Button loadBtn = new Button("Load Quiz");
        Button startBtn = new Button("Start Quiz");
        startBtn.setDisable(true);

        Label status = new Label("No quiz loaded.");
        status.setStyle("-fx-text-fill: #666;");

        HBox buttons = new HBox(10, loadBtn, startBtn);
        buttons.setPadding(new Insets(10,0,0,0));

        BorderPane.setMargin(title, new Insets(0,0,10,0));
        root.setTop(title);
        root.setCenter(desc);
        root.setBottom(new VBoxBuilder().addAll(status, buttons));

        loadBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select Quiz JSON");
            chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            File f = chooser.showOpenDialog(null);
            if (f != null) {
                try {
                    Quiz quiz = QuizIO.loadQuiz(f);
                    GameManager.getInstance().loadQuiz(quiz);
                    desc.setText(quiz.getDescription());
                    status.setText("Loaded: " + quiz.getTitle() + " (" + quiz.getTotalQuestions() + " questions)");
                    startBtn.setDisable(false);
                } catch (MismatchedInputException ex) {
                    alert("Invalid JSON structure", ex.getMessage());
                } catch (IOException ex) {
                    alert("I/O Error", ex.getMessage());
                } catch (Exception ex) {
                    alert("Error", ex.getMessage());
                }
            }
        });

        startBtn.setOnAction(e -> router.showGame());

        this.scene = new Scene(root, 760, 520);
    }

    private void alert(String header, String content) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Error");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    public Scene getScene() { return scene; }

    // Small helper VBox builder for simple stacking
    static class VBoxBuilder extends javafx.scene.layout.VBox {
        VBoxBuilder() { super(6); setPadding(new Insets(6,0,0,0)); }
        VBoxBuilder addAll(javafx.scene.Node... nodes) { getChildren().addAll(nodes); return this; }
    }
}
