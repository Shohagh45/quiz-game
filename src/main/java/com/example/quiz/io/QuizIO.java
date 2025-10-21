package com.example.quiz.io;

import com.example.quiz.model.Quiz;
import com.example.quiz.view.ResultsView.ResultRow;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.*;
import java.nio.file.Files;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class QuizIO {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static String quizId(Quiz quiz) {
        String title = quiz.getTitle() == null ? "quiz" : quiz.getTitle();
        return title.trim().toLowerCase().replaceAll("[^a-z0-9]+","-");
    }

    public static Quiz loadQuiz(File file) throws IOException {
        MAPPER.findAndRegisterModules();
        try (InputStream in = new FileInputStream(file)) {
            return MAPPER.readValue(in, Quiz.class);
        }
    }

    private static File resultsFileFor(Quiz quiz) throws IOException {
        Files.createDirectories(new File("results").toPath());
        return new File("results", quizId(quiz) + "-results.json");
    }

    public static void appendResult(Quiz quiz, String playerName, int total, int correct) throws IOException {
        File f = resultsFileFor(nonNull(quiz));
        ObjectNode root;
        if (f.exists()) {
            try (InputStream in = new FileInputStream(f)) {
                root = (ObjectNode) MAPPER.readTree(in);
            }
        } else {
            root = MAPPER.createObjectNode();
            root.put("quizId", quizId(quiz));
            root.put("name", quiz.getTitle() == null ? "" : quiz.getTitle());
            root.set("results", MAPPER.createArrayNode());
        }

        ArrayNode results = (ArrayNode) root.withArray("results");
        ObjectNode entry = MAPPER.createObjectNode();
        entry.put("playerName", playerName == null ? "" : playerName);
        entry.put("totalQuestions", total);
        entry.put("correctQuestions", correct);
        entry.put("date", Instant.now().toString());
        results.add(entry);

        try (OutputStream out = new FileOutputStream(f)) {
            MAPPER.writerWithDefaultPrettyPrinter().writeValue(out, root);
        }
    }

    public static List<ResultRow> readResults(Quiz quiz) throws IOException {
        File f = resultsFileFor(nonNull(quiz));
        List<ResultRow> rows = new ArrayList<>();
        if (!f.exists()) return rows;

        try (InputStream in = new FileInputStream(f)) {
            JsonNode root = MAPPER.readTree(in);
            JsonNode arr = root.get("results");
            if (arr != null && arr.isArray()) {
                for (JsonNode n : arr) {
                    rows.add(new ResultRow(
                            n.path("playerName").asText(""),
                            n.path("totalQuestions").asInt(0),
                            n.path("correctQuestions").asInt(0),
                            n.path("date").asText("")
                    ));
                }
            }
        }
        return rows;
    }

    private static <T> T nonNull(T v) {
        if (v == null) throw new IllegalArgumentException("Quiz cannot be null");
        return v;
    }
}
