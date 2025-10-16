package com.example.quiz;

import com.example.quiz.model.Quiz;
import com.example.quiz.state.GameManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ScoringTest {

    @Test
    public void scoreIncrementsOnCorrect() throws Exception {
        String json = "{\"title\":\"T\",\"description\":\"D\",\"pages\":[{\"timeLimit\":5,\"elements\":[{\"type\":\"boolean\",\"name\":\"q1\",\"title\":\"t\",\"correctAnswer\":true,\"isRequired\":true}]}]}";
        ObjectMapper om = new ObjectMapper();
        Quiz quiz = om.readValue(json, Quiz.class);

        GameManager gm = GameManager.getInstance();
        gm.loadQuiz(quiz);
        assertEquals(0, gm.scoreProperty().get());
        gm.answerCurrent(true);
        assertEquals(1, gm.scoreProperty().get());
    }
}
