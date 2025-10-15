package com.example.quiz.model;

import java.util.List;

public class Page {
    private int timeLimit;
    private List<Element> elements;

    public int getTimeLimit() { return timeLimit; }
    public void setTimeLimit(int timeLimit) { this.timeLimit = timeLimit; }

    public List<Element> getElements() { return elements; }
    public void setElements(List<Element> elements) { this.elements = elements; }
}
