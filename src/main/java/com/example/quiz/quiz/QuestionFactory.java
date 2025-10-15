package com.example.quiz.quiz;

import com.example.quiz.model.Element;
import com.example.quiz.model.Page;

import java.util.Arrays;

public class QuestionFactory {
    public static Question from(Page page) {
        if (page.getElements()==null || page.getElements().isEmpty())
            throw new IllegalArgumentException("Page has no elements");
        Element e = page.getElements().get(0); // assume one per page
        String type = e.getType();
        String id = e.getName();
        String text = e.getTitle();
        int time = page.getTimeLimit();

        if ("radiogroup".equalsIgnoreCase(type)) {
            return new ChoiceQuestion(id, text, time,
                    Arrays.asList(e.getChoices()), e.getCorrectAnswer());
        } else if ("boolean".equalsIgnoreCase(type)) {
            boolean correct = e.getCorrectAnswerBool() != null ? e.getCorrectAnswerBool() :
                    "true".equalsIgnoreCase(String.valueOf(e.getCorrectAnswer()));
            return new BooleanQuestion(id, text, time, correct);
        } else {
            throw new UnsupportedOperationException("Unsupported question type: " + type);
        }
    }
}
