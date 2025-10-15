package com.example.quiz.quiz;

import java.util.Arrays;
import java.util.List;

public class ChoiceQuestion extends Question {
    private final List<String> choices;
    private final String correct;

    public ChoiceQuestion(String id, String text, int timeLimit, List<String> choices, String correct) {
        super(id, text, timeLimit);
        this.choices = choices;
        this.correct = correct;
    }

    public List<String> getChoices() { return choices; }

    @Override
    public boolean isCorrect(Object answer) {
        return answer != null && correct.equals(answer.toString());
    }
}
