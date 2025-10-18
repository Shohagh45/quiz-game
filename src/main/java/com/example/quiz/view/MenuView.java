package com.example.quiz.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class MenuView {
    private final Button loadBtn = new Button("Load Quiz");
    private final Button startBtn = new Button("Start Quiz");
    private final TextArea descArea = new TextArea();
    private final Label statusLabel = new Label("No quiz loaded.");
    private final BorderPane root = new BorderPane();
    private final Scene scene = new Scene(root, 900, 600);

    public MenuView() {

        Label title = new Label("JavaFX Quiz Game");
        title.setFont(Font.font(20));
        HBox top = new HBox(12, title);
        top.setPadding(new Insets(12));
        root.setTop(top);


        descArea.setEditable(false);
        descArea.setWrapText(true);
        VBox center = new VBox(10, new Label("Description"), descArea);
        center.setPadding(new Insets(12));
        root.setCenter(center);


        startBtn.setDisable(true);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox bottom = new HBox(10, loadBtn, startBtn, spacer, statusLabel);
        bottom.setPadding(new Insets(12));
        root.setBottom(bottom);


        // scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    }


    public Button getLoadBtn()       { return loadBtn; }
    public Button getStartBtn()      { return startBtn; }
    public TextArea getDescArea()    { return descArea; }
    public Label getStatusLabel()    { return statusLabel; }
    public Scene getScene()          { return scene; }
    public BorderPane getRoot()      { return root; }
}
