package com.example.quiz.quiz;

public class BooleanQuestion extends Question {
    private final boolean correct;

    public BooleanQuestion(String id, String text, int timeLimit, boolean correct) {
        super(id, text, timeLimit);
        this.correct = correct;
    }

    @Override
    public boolean isCorrect(Object answer) {
        if (answer instanceof Boolean b) return b == correct;
        if (answer == null) return false;
        return Boolean.parseBoolean(answer.toString()) == correct;
    }
}
