package com.groep1e;

import java.util.ArrayList;

public class Question {
    private String question;
    private String answer;
    private static ArrayList<Question> questionList = new ArrayList<>();

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public static ArrayList<Question> getQuestionList() {
        return questionList;
    }

    public static void addQuestion(Question question) {
        questionList.add(question);
    }

    public static void resetQuestonList() {
        questionList = new ArrayList<>();
    }

}
