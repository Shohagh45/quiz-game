package com.example.quiz.quiz;

import com.example.quiz.model.Element;
import com.example.quiz.model.Page;

import java.util.List;

public class QuestionFactory {

    public static Question from(Page page) {
        if (page.getElements() == null || page.getElements().isEmpty()) {
            throw new IllegalArgumentException("Page has no elements");
        }

        Element e = page.getElements().get(0);
        String type = e.getType();
        String id   = e.getName();
        String text = e.getTitle();
        int time    = page.getTimeLimit();

        if ("radiogroup".equalsIgnoreCase(type)) {

            List<String> choices = e.getChoices() != null ? e.getChoices() : List.of();


            String correct = e.getCorrectAnswerString();
            if (correct == null && e.getCorrectAnswerBoolean() != null) {

                correct = String.valueOf(e.getCorrectAnswerBoolean());
            }
            return new ChoiceQuestion(id, text, time, choices, correct);

        } else if ("boolean".equalsIgnoreCase(type)) {

            Boolean correct = e.getCorrectAnswerBoolean();
            if (correct == null) {

                String s = e.getCorrectAnswerString();
                correct = s != null ? Boolean.parseBoolean(s) : Boolean.FALSE;
            }
            return new BooleanQuestion(id, text, time, correct);

        } else {
            throw new UnsupportedOperationException("Unsupported question type: " + type);
        }
    }
}
