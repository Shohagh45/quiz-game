package com.example.quiz.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Element {

    private String type;
    private String name;
    private String title;


    private List<String> choices;

    private String choicesOrder;


    @JsonProperty("correctAnswer")
    private Object correctAnswer;


    @JsonAlias({"isRequired", "required"})
    private boolean required;

    private String labelTrue;
    private String labelFalse;



    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getChoices() { return choices; }
    public void setChoices(List<String> choices) { this.choices = choices; }

    public String getChoicesOrder() { return choicesOrder; }
    public void setChoicesOrder(String choicesOrder) { this.choicesOrder = choicesOrder; }


    public Object getCorrectAnswerRaw() { return correctAnswer; }
    public void setCorrectAnswerRaw(Object correctAnswer) { this.correctAnswer = correctAnswer; }


    public String getCorrectAnswerString() {
        return (correctAnswer instanceof String) ? (String) correctAnswer : null;
    }
    public Boolean getCorrectAnswerBoolean() {
        return (correctAnswer instanceof Boolean) ? (Boolean) correctAnswer : null;
    }

    public boolean isRequired() { return required; }
    public void setRequired(boolean required) { this.required = required; }

    public String getLabelTrue() { return labelTrue; }
    public void setLabelTrue(String labelTrue) { this.labelTrue = labelTrue; }

    public String getLabelFalse() { return labelFalse; }
    public void setLabelFalse(String labelFalse) { this.labelFalse = labelFalse; }
}
