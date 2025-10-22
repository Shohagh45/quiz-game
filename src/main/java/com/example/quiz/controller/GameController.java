package com.example.quiz.controller;

import com.example.quiz.Router;
import com.example.quiz.quiz.ChoiceQuestion;
import com.example.quiz.quiz.Question;
import com.example.quiz.state.GameManager;
import com.example.quiz.view.GameView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.util.Duration;

public class GameController {
    private final Router router;
    private final GameView view;
    private final GameManager gm = GameManager.getInstance();

    private Timeline timer;
    private int totalForTimer = 0;
    private boolean inputLocked = false;

    public GameController(Router router, GameView view) {
        this.router = router;
        this.view = view;
        hookBindings();
        hookEvents();
    }

    private void hookBindings() {

        view.scoreLabel().textProperty().bind(gm.scoreProperty().asString("Score: %d"));
        view.progressLabel().textProperty().bind(
                Bindings.createStringBinding(
                        () -> "Question " + (gm.currentIndexProperty().get() + 1) + " of " + gm.getTotalQuestions(),
                        gm.currentIndexProperty()
                )
        );
    }

    private void hookEvents() {

        view.nameField().setOnAction(e -> begin());


        view.submitBtn().setOnAction(e -> {
            if (inputLocked) return;
            lockInput(true);
            try {
                Question q = gm.getCurrentQuestion();
                Object answer = null;
                if (q instanceof ChoiceQuestion) {
                    Toggle t = view.choicesGroup().getSelectedToggle();
                    if (t != null) answer = ((RadioButton) t).getText();
                }
                gm.answerCurrent(answer);
                nextQuestion();
            } finally {
                lockInput(false);
            }
        });


        view.trueBtn().setOnAction(e -> {
            if (inputLocked) return;
            lockInput(true);
            try { gm.answerCurrent(true); nextQuestion(); }
            finally { lockInput(false); }
        });
        view.falseBtn().setOnAction(e -> {
            if (inputLocked) return;
            lockInput(true);
            try { gm.answerCurrent(false); nextQuestion(); }
            finally { lockInput(false); }
        });


        view.skipBtn().setOnAction(e -> {
            if (inputLocked) return;
            lockInput(true);
            try { gm.answerCurrent(null); nextQuestion(); }
            finally { lockInput(false); }
        });


        view.finishBtn().setOnAction(e -> router.showResults());
    }

    private void begin() {
        String name = view.nameField().getText() == null ? "" : view.nameField().getText().trim();
        if (name.isEmpty()) {
            view.questionText().setText("Please enter your name to start.");
            return;
        }
        gm.playerNameProperty().set(name);
        view.nameField().setDisable(true);
        nextQuestion();
    }

    private void nextQuestion() {
        stopTimer();

        if (!gm.hasNext()) {
            router.showResults();
            return;
        }

        Question q = gm.getCurrentQuestion();


        view.questionText().setText(q.getText());
        view.choicesBox().getChildren().clear();
        view.choicesGroup().getToggles().clear();

        view.trueBtn().setVisible(false);
        view.falseBtn().setVisible(false);
        view.submitBtn().setVisible(true);
        view.finishBtn().setVisible(false);

        if (q instanceof ChoiceQuestion cq) {

            for (String c : cq.getChoices()) {
                RadioButton rb = new RadioButton(c);
                rb.setToggleGroup(view.choicesGroup());
                view.choicesBox().getChildren().add(rb);
            }

            view.submitBtn().setDisable(true);
            view.choicesGroup().selectedToggleProperty().addListener((obs, oldT, newT) ->
                    view.submitBtn().setDisable(newT == null));
        } else {

            view.submitBtn().setVisible(false);
            view.trueBtn().setVisible(true);
            view.falseBtn().setVisible(true);
        }


        int idx = gm.currentIndexProperty().get();
        boolean last = (idx == gm.getTotalQuestions() - 1);
        view.finishBtn().setVisible(last);


        startTimer(q.getTimeLimit());
    }

    private void startTimer(int seconds) {
        if (seconds <= 0) {
            view.timeLabel().setText("--");
            view.timeBar().setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            return;
        }

        totalForTimer = seconds;
        view.timeLabel().setText(seconds + "s");
        view.timeBar().setProgress(1.0);

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {

            String txt = view.timeLabel().getText();
            int remaining = (txt.endsWith("s") ? Integer.parseInt(txt.substring(0, txt.length() - 1)) : 0) - 1;
            view.timeLabel().setText(remaining + "s");
            view.timeBar().setProgress(Math.max(0, (double) remaining / totalForTimer));
            if (remaining <= 0) {
                stopTimer();
                gm.answerCurrent(null);
                nextQuestion();
            }
        }));
        timer.setCycleCount(seconds);
        timer.playFromStart();
    }

    private void stopTimer() {
        if (timer != null) timer.stop();
    }


    private void lockInput(boolean lock) {
        inputLocked = lock;
        boolean submitShouldBeDisabled =
                lock || (view.submitBtn().isVisible() && view.choicesGroup().getSelectedToggle() == null);
        view.submitBtn().setDisable(submitShouldBeDisabled);
        view.trueBtn().setDisable(lock);
        view.falseBtn().setDisable(lock);
        view.skipBtn().setDisable(lock);
        view.nameField().setDisable(lock && gm.currentIndexProperty().get() == 0);
    }
}
