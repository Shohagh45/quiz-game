package com.example.quiz.model;

public class Element {
    private String type;
    private String name;
    private String title;
    private String[] choices;
    private String choicesOrder;
    private String correctAnswer;
    private Boolean correctAnswerBool;
    private boolean isRequired;
    private String labelTrue;
    private String labelFalse;

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String[] getChoices() { return choices; }
    public void setChoices(String[] choices) { this.choices = choices; }
    public String getChoicesOrder() { return choicesOrder; }
    public void setChoicesOrder(String choicesOrder) { this.choicesOrder = choicesOrder; }
    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
    public Boolean getCorrectAnswerBool() { return correctAnswerBool; }
    public void setCorrectAnswerBool(Boolean correctAnswerBool) { this.correctAnswerBool = correctAnswerBool; }
    public boolean isRequired() { return isRequired; }
    public void setRequired(boolean required) { isRequired = required; }
    public String getLabelTrue() { return labelTrue; }
    public void setLabelTrue(String labelTrue) { this.labelTrue = labelTrue; }
    public String getLabelFalse() { return labelFalse; }
    public void setLabelFalse(String labelFalse) { this.labelFalse = labelFalse; }
}
