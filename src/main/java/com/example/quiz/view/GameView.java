package com.example.quiz.view;

import com.example.quiz.Router;
import com.example.quiz.state.GameManager;
import com.example.quiz.quiz.ChoiceQuestion;
import com.example.quiz.quiz.Question;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class GameView {
    private final Router router;
    private final Scene scene;

    private Timeline timer;
    private int remaining;

    public GameView(Router router) {
        this.router = router;
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        Label header = new Label("Quiz In Progress");
        header.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Top bar: Player, Score, Progress
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name and press Enter");
        Label scoreLabel = new Label();
        ProgressBar timeBar = new ProgressBar(1.0);
        Label timeLabel = new Label("--s");

        scoreLabel.textProperty().bind(Bindings.concat("Score: ", GameManager.getInstance().scoreProperty().asString()));
        Label progressLabel = new Label();
        progressLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        "Question " + (GameManager.getInstance().currentIndexProperty().get() + 1) +
                                " of " + GameManager.getInstance().getTotalQuestions(),
                GameManager.getInstance().currentIndexProperty()));

        HBox top = new HBox(12, header, new Label(" | "), new Label("Name:"), nameField,
                new Label(" | "), scoreLabel, new Label(" | "), progressLabel,
                new Label(" | "), timeLabel, timeBar);
        top.setPadding(new Insets(10));
        root.setTop(top);

        // Center question pane
        VBox center = new VBox(10);
        center.setPadding(new Insets(10));
        Label questionText = new Label("Enter your name to begin.");
        questionText.setWrapText(true);
        ToggleGroup choicesGroup = new ToggleGroup();
        Button trueBtn = new Button("True");
        Button falseBtn = new Button("False");

        VBox choicesBox = new VBox(6);
        center.getChildren().addAll(questionText, choicesBox, trueBtn, falseBtn);
        root.setCenter(center);
        trueBtn.setVisible(false);
        falseBtn.setVisible(false);

        // Bottom actions
        Button submitBtn = new Button("Submit Answer");
        submitBtn.setDisable(true);
        Button skipBtn = new Button("Skip");
        HBox bottom = new HBox(10, submitBtn, skipBtn);
        bottom.setPadding(new Insets(10));
        root.setBottom(bottom);

        nameField.setOnAction(e -> {
            String name = nameField.getText() == null ? "" : nameField.getText().trim();
            if (name.isEmpty()) {
                alert("Validation", "Please enter your name to start.");
                return;
            }
            GameManager.getInstance().playerNameProperty().set(name);
            nextQuestion(questionText, choicesBox, choicesGroup, trueBtn, falseBtn, submitBtn, timeBar, timeLabel);
        });

        submitBtn.setOnAction(e -> {
            Question q = GameManager.getInstance().getCurrentQuestion();
            Object answer = null;
            if (q instanceof ChoiceQuestion) {
                Toggle selected = choicesGroup.getSelectedToggle();
                if (selected != null) {
                    answer = ((RadioButton) selected).getText();
                }
            } else {
                // handled via T/F buttons setting remaining selection
            }
            if (answer == null && !(q instanceof ChoiceQuestion)) {
                alert("Validation", "Select True or False.");
                return;
            }
            GameManager.getInstance().answerCurrent(answer);
            nextQuestion(questionText, choicesBox, choicesGroup, trueBtn, falseBtn, submitBtn, timeBar, timeLabel);
        });

        trueBtn.setOnAction(e -> {
            GameManager.getInstance().answerCurrent(true);
            nextQuestion(questionText, choicesBox, choicesGroup, trueBtn, falseBtn, submitBtn, timeBar, timeLabel);
        });
        falseBtn.setOnAction(e -> {
            GameManager.getInstance().answerCurrent(false);
            nextQuestion(questionText, choicesBox, choicesGroup, trueBtn, falseBtn, submitBtn, timeBar, timeLabel);
        });
        skipBtn.setOnAction(e -> {
            GameManager.getInstance().answerCurrent(null);
            nextQuestion(questionText, choicesBox, choicesGroup, trueBtn, falseBtn, submitBtn, timeBar, timeLabel);
        });

        this.scene = new Scene(root, 860, 560);
    }

    private void nextQuestion(Label questionText, VBox choicesBox, ToggleGroup choicesGroup,
                              Button trueBtn, Button falseBtn, Button submitBtn,
                              ProgressBar timeBar, Label timeLabel) {
        if (timer != null) timer.stop();

        if (!GameManager.getInstance().hasNext()) {
            router.showResults();
            return;
        }
        Question q = GameManager.getInstance().getCurrentQuestion();
        questionText.setText(q.getText());

        choicesBox.getChildren().clear();
        choicesGroup.getToggles().clear();
        trueBtn.setVisible(false);
        falseBtn.setVisible(false);
        submitBtn.setDisable(false);

        if (q instanceof ChoiceQuestion cq) {
            for (String c : cq.getChoices()) {
                RadioButton rb = new RadioButton(c);
                rb.setToggleGroup(choicesGroup);
                choicesBox.getChildren().add(rb);
            }
            trueBtn.setVisible(false);
            falseBtn.setVisible(false);
            submitBtn.setVisible(true);
        } else {
            // boolean
            submitBtn.setVisible(false);
            trueBtn.setVisible(true);
            falseBtn.setVisible(true);
        }

        // Timer
        remaining = q.getTimeLimit();
        timeLabel.setText(remaining + "s");
        timeBar.setProgress(1.0);
        if (remaining > 0) {
            timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                remaining--;
                timeLabel.setText(remaining + "s");
                timeBar.setProgress(Math.max(0, (double) remaining / q.getTimeLimit()));
                if (remaining <= 0) {
                    timer.stop();
                    // auto move on as incorrect
                    GameManager.getInstance().answerCurrent(null);
                    nextQuestion(questionText, choicesBox, choicesGroup, trueBtn, falseBtn, submitBtn, timeBar, timeLabel);
                }
            }));
            timer.setCycleCount(q.getTimeLimit());
            timer.playFromStart();
        } else {
            timeLabel.setText("--");
            timeBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        }
    }

    private void alert(String header, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Info");
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }

    public Scene getScene() { return scene; }
}
