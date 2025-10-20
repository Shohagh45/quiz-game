package com.example.quiz.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class GameView {

    // Controls the controller will use
    private final TextField nameField = new TextField();
    private final Label scoreLabel = new Label();
    private final Label progressLabel = new Label();

    private final ProgressBar timeBar = new ProgressBar(1.0);
    private final Label timeLabel = new Label("--s");

    private final Label questionText = new Label("Enter your name to begin.");
    private final VBox choicesBox = new VBox(6);
    private final ToggleGroup choicesGroup = new ToggleGroup();

    private final Button trueBtn = new Button("True");
    private final Button falseBtn = new Button("False");
    private final Button submitBtn = new Button("Submit Answer");
    private final Button skipBtn = new Button("Skip");
    private final Button finishBtn = new Button("Finish Quiz");

    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root, 860, 560);

    public GameView() {
        root.setPadding(new Insets(16));

        Label header = new Label("Quiz In Progress");
        header.setFont(Font.font(20));

        nameField.setPromptText("Enter your name and press Enter");

        HBox top = new HBox(12, header, new Label("|"), new Label("Name:"), nameField,
                new Label("|"), scoreLabel, new Label("|"), progressLabel,
                new Label("|"), timeLabel, timeBar);
        top.setPadding(new Insets(10));
        root.setTop(top);

        questionText.setWrapText(true);
        trueBtn.setVisible(false);
        falseBtn.setVisible(false);
        submitBtn.setDisable(true);
        finishBtn.setVisible(false);

        VBox center = new VBox(10, questionText, choicesBox, trueBtn, falseBtn);
        center.setPadding(new Insets(10));
        root.setCenter(center);

        HBox bottom = new HBox(10, submitBtn, skipBtn, finishBtn);
        bottom.setPadding(new Insets(10));
        root.setBottom(bottom);
    }

    // Getters for controller
    public Scene getScene()             { return scene; }
    public TextField nameField()        { return nameField; }
    public Label scoreLabel()           { return scoreLabel; }
    public Label progressLabel()        { return progressLabel; }
    public ProgressBar timeBar()        { return timeBar; }
    public Label timeLabel()            { return timeLabel; }
    public Label questionText()         { return questionText; }
    public VBox choicesBox()            { return choicesBox; }
    public ToggleGroup choicesGroup()   { return choicesGroup; }
    public Button trueBtn()             { return trueBtn; }
    public Button falseBtn()            { return falseBtn; }
    public Button submitBtn()           { return submitBtn; }
    public Button skipBtn()             { return skipBtn; }
    public Button finishBtn()           { return finishBtn; }
}
