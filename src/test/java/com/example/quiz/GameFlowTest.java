package com.example.quiz;

import com.example.quiz.model.Element;
import com.example.quiz.model.Page;
import com.example.quiz.model.Quiz;
import com.example.quiz.state.GameManager;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameFlowTest {

    private Quiz twoQuestions() {
        Element e1 = new Element();
        e1.setType("boolean"); e1.setName("q1"); e1.setTitle("T?"); e1.setCorrectAnswerRaw(Boolean.TRUE);
        Page p1 = new Page(); p1.setTimeLimit(5); p1.setElements(List.of(e1));

        Element e2 = new Element();
        e2.setType("boolean"); e2.setName("q2"); e2.setTitle("F?"); e2.setCorrectAnswerRaw(Boolean.FALSE);
        Page p2 = new Page(); p2.setTimeLimit(5); p2.setElements(List.of(e2));

        Quiz q = new Quiz(); q.setTitle("T"); q.setPages(List.of(p1, p2));
        return q;
    }

    @Test
    void advancesOnWrongOrNull() {
        GameManager gm = GameManager.getInstance();
        gm.loadQuiz(twoQuestions());


        gm.answerCurrent(false);
        assertEquals(0, gm.getScore());
        assertEquals(1, gm.getCurrentIndex());


        gm.answerCurrent(null);
        assertEquals(0, gm.getScore());
        assertEquals(2, gm.getCurrentIndex());
        assertFalse(gm.hasNext());
    }
}
