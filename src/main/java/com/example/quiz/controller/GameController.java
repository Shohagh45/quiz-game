package com.example.quiz.controller;
import com.example.quiz.state.GameManager;
import com.example.quiz.Router;
import com.example.quiz.view.GameView;

public class GameController {
    private final Router router;
    private final GameView view;

    public GameController(Router router, GameView view) {
        this.router = router;
        this.view = view;

    }
    private void hookEvents() {
        view.nameField().setOnAction(e -> {
            String name = view.nameField().getText() == null ? "" : view.nameField().getText().trim();
            if (name.isEmpty()) return;
            GameManager.getInstance().playerNameProperty().set(name);
            // For now just prove it reacts:
            view.questionText().setText("Starting…");
            // We'll move full nextQuestion() in the next step
        });
    }
}
