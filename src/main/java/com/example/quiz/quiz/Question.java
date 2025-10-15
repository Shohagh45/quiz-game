package com.example.quiz.quiz;

public abstract class Question {
    private final String id;
    private final String text;
    private final int timeLimit;

    protected Question(String id, String text, int timeLimit) {
        this.id = id;
        this.text = text;
        this.timeLimit = timeLimit;
    }

    public String getId() { return id; }
    public String getText() { return text; }
    public int getTimeLimit() { return timeLimit; }

    public abstract boolean isCorrect(Object answer);
}
