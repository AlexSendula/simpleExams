package com.groep1e;

import java.util.ArrayList;

public class Question {
    private String vraag;
    private String antwoord;
    private static ArrayList<Question> questionslist = new ArrayList<Question>();

    public Question(String vraag, String antwoord) {
        this.vraag = vraag;
        this.antwoord = antwoord;
    }

    public String getVraag() {
        return vraag;
    }

    public String getAntwoord() {
        return antwoord;
    }

    public static ArrayList<Question> getQuestionslist() {
        return questionslist;
    }

    public static void addQuestion(Question question) {
        questionslist.add(question);
    }
}
