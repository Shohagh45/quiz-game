package com.example.quiz;

import com.example.quiz.io.QuizIO;
import com.example.quiz.model.Quiz;
import com.example.quiz.view.ResultsView;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultsIoTest {
    private String originalDir;
    @BeforeEach
    void useTempDir() throws Exception {
        originalDir = System.getProperty("user.dir");
        File tmp = Files.createTempDirectory("quiz-results-test").toFile();
        System.setProperty("user.dir", tmp.getAbsolutePath());
    }
    @AfterEach
    void restoreDir() { System.setProperty("user.dir", originalDir); }

    private Quiz quiz(String title) { Quiz q = new Quiz(); q.setTitle(title); return q; }

    @Test
    void appendAndRead() throws Exception {

        Quiz quiz = quiz("Java Basics Quiz");


        java.nio.file.Path resultsPath = java.nio.file.Paths.get("results", "java-basics-quiz-results.json");
        java.nio.file.Files.deleteIfExists(resultsPath);
        java.nio.file.Files.createDirectories(resultsPath.getParent());

        QuizIO.appendResult(quiz, "Alice", 3, 2);
        QuizIO.appendResult(quiz, "Bob",   3, 3);

        List<ResultsView.ResultRow> rows = QuizIO.readResults(quiz);
        assertEquals(2, rows.size());
        assertEquals("Alice", rows.get(0).getPlayerName());
        assertEquals(3, rows.get(1).getCorrectQuestions());
    }
}
