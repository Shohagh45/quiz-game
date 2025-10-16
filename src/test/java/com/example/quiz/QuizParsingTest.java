package com.example.quiz;

import com.example.quiz.io.QuizIO;
import com.example.quiz.model.Quiz;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class QuizParsingTest {

    @Test
    public void loadSampleQuiz() throws IOException {
        File f = new File("src/test/resources/sample-quiz.json");
        Quiz quiz = QuizIO.loadQuiz(f);
        assertNotNull(quiz);
        assertEquals("Java Basics Quiz", quiz.getTitle());
        assertEquals(3, quiz.getPages().size());
    }
}
