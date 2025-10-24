package com.example.quiz;

import com.example.quiz.model.Element;
import com.example.quiz.model.Page;
import com.example.quiz.quiz.BooleanQuestion;
import com.example.quiz.quiz.ChoiceQuestion;
import com.example.quiz.quiz.Question;
import com.example.quiz.quiz.QuestionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuestionFactoryTest {

    @Test
    void buildsMultipleChoice() {
        Element e = new Element();
        e.setType("radiogroup"); e.setName("q1"); e.setTitle("Pick one");
        e.setChoices(List.of("A","B","C")); e.setCorrectAnswerRaw("B");
        Page p = new Page(); p.setTimeLimit(20); p.setElements(List.of(e));

        Question q = QuestionFactory.from(p);
        assertTrue(q instanceof ChoiceQuestion);
        ChoiceQuestion cq = (ChoiceQuestion) q;
        assertEquals("Pick one", cq.getText());
        assertEquals(3, cq.getChoices().size());
        assertTrue(cq.isCorrect("B"));
        assertFalse(cq.isCorrect("A"));
    }

    @Test
    void buildsBoolean() {
        Element e = new Element();
        e.setType("boolean"); e.setName("q2"); e.setTitle("True?");
        e.setCorrectAnswerRaw(Boolean.TRUE);
        Page p = new Page(); p.setTimeLimit(10); p.setElements(List.of(e));

        Question q = QuestionFactory.from(p);
        assertTrue(q instanceof BooleanQuestion);
        assertTrue(((BooleanQuestion) q).isCorrect(true));
        assertFalse(((BooleanQuestion) q).isCorrect(false));
    }
}
