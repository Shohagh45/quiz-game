package com.example.quiz.state;

import com.example.quiz.model.Page;
import com.example.quiz.model.Quiz;
import com.example.quiz.quiz.Question;
import com.example.quiz.quiz.QuestionFactory;
import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static GameManager INSTANCE;

    private final StringProperty playerName = new SimpleStringProperty("");
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty currentIndex = new SimpleIntegerProperty(0);
    private final List<Question> questions = new ArrayList<>();
    private Quiz quiz;

    private GameManager() {}

    public static synchronized GameManager getInstance() {
        if (INSTANCE == null) INSTANCE = new GameManager();
        return INSTANCE;
    }

    public void reset() {
        playerName.set("");
        score.set(0);
        currentIndex.set(0);
        questions.clear();
        quiz = null;
    }

    public void loadQuiz(Quiz quiz) {
        reset();
        this.quiz = quiz;
        if (quiz.getPages()!=null) {
            for (Page p : quiz.getPages()) {
                questions.add(QuestionFactory.from(p));
            }
        }
    }

    public boolean hasNext() {
        return currentIndex.get() < questions.size();
    }

    public Question getCurrentQuestion() {
        if (!hasNext()) return null;
        return questions.get(currentIndex.get());
    }

    public void answerCurrent(Object answer) {
        Question q = getCurrentQuestion();
        if (q != null && q.isCorrect(answer)) {
            score.set(score.get() + 1);
        }
        currentIndex.set(currentIndex.get() + 1);
    }

    public int getTotalQuestions() { return questions.size(); }
    public Quiz getQuiz() { return quiz; }

    // Properties for Observer pattern
    public StringProperty playerNameProperty() { return playerName; }
    public IntegerProperty scoreProperty() { return score; }
    public IntegerProperty currentIndexProperty() { return currentIndex; }
}
