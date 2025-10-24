package com.example.quiz;

import com.example.quiz.model.Element;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElementParsingTest {
    private final ObjectMapper m = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    void mapsIsRequiredAlias() throws Exception {
        String json = "{\"type\":\"radiogroup\",\"name\":\"q1\",\"title\":\"T\",\"isRequired\":true}";
        Element e = m.readValue(json, Element.class);
        assertTrue(e.isRequired());
    }

    @Test
    void correctAnswerStringAndBoolean() throws Exception {
        Element e1 = m.readValue("{\"type\":\"radiogroup\",\"correctAnswer\":\"X\"}", Element.class);
        assertEquals("X", e1.getCorrectAnswerString());
        Element e2 = m.readValue("{\"type\":\"boolean\",\"correctAnswer\":false}", Element.class);
        assertEquals(Boolean.FALSE, e2.getCorrectAnswerBoolean());
    }
}
