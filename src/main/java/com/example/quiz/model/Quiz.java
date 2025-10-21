package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Quiz {
    private String quizId = "quiz001";
    private String title;
    private String description;
    private List<Page> pages;


    public String getQuizId() { return quizId; }
    public void setQuizId(String id) { this.quizId = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<Page> getPages() { return pages; }
    public void setPages(List<Page> pages) { this.pages = pages; }

    public int getTotalQuestions() {
        return pages == null ? 0 : pages.size();
    }
}
