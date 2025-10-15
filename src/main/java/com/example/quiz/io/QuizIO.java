package com.example.quiz.io;

import com.example.quiz.model.Element;
import com.example.quiz.model.Page;
import com.example.quiz.model.Quiz;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class QuizIO {
    private static final ObjectMapper mapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);

    public static Quiz loadQuiz(File file) throws IOException {
        return mapper.readValue(file, Quiz.class);
    }

    public static void saveResult(Quiz quiz, String playerName, int totalQuestions, int correct) throws IOException {
        String fileName = quiz.getQuizId() + "-results.json";
        Path resultsDir = Path.of("results");
        Files.createDirectories(resultsDir);
        Path out = resultsDir.resolve(fileName);

        ObjectNode root;
        if (Files.exists(out)) {
            root = (ObjectNode) mapper.readTree(out.toFile());
        } else {
            root = mapper.createObjectNode();
            root.put("quizId", quiz.getQuizId());
            root.put("name", quiz.getTitle());
            root.putArray("results");
        }

        ObjectNode entry = mapper.createObjectNode();
        entry.put("playerName", playerName);
        entry.put("totalQuestions", totalQuestions);
        entry.put("correctQuestions", correct);
        entry.put("date", Instant.now().toString());

        root.withArray("results").add(entry);
        mapper.writerWithDefaultPrettyPrinter().writeValue(out.toFile(), root);
    }
}
