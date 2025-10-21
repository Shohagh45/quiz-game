package com.example.quiz.view;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ResultsView {

    public static class ResultRow {
        private final String playerName;
        private final int totalQuestions;
        private final int correctQuestions;
        private final String date;

        public ResultRow(String playerName, int totalQuestions, int correctQuestions, String date) {
            this.playerName = playerName; this.totalQuestions = totalQuestions;
            this.correctQuestions = correctQuestions; this.date = date;
        }
        public String getPlayerName() { return playerName; }
        public int getTotalQuestions() { return totalQuestions; }
        public int getCorrectQuestions() { return correctQuestions; }
        public String getDate() { return date; }
    }

    private final TableView<ResultRow> table = new TableView<>();
    private final Button menuBtn = new Button("Back to Menu");
    private final Scene scene;

    public ResultsView() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));

        TableColumn<ResultRow, String> c1 = new TableColumn<>("Player");
        c1.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        TableColumn<ResultRow, Integer> c2 = new TableColumn<>("Total");
        c2.setCellValueFactory(new PropertyValueFactory<>("totalQuestions"));
        TableColumn<ResultRow, Integer> c3 = new TableColumn<>("Correct");
        c3.setCellValueFactory(new PropertyValueFactory<>("correctQuestions"));
        TableColumn<ResultRow, String> c4 = new TableColumn<>("Date");
        c4.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(c1, c2, c3, c4);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        HBox bottom = new HBox(10, menuBtn);
        bottom.setPadding(new Insets(10));

        root.setCenter(table);
        root.setBottom(bottom);
        scene = new Scene(root, 860, 560);
    }

    public TableView<ResultRow> table() { return table; }
    public Button menuBtn() { return menuBtn; }
    public Scene getScene() { return scene; }
}
