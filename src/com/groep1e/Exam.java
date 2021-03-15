package com.groep1e;

import java.util.*;

public class Exam {
    private String name;
    private String difficulty;
    private String examLength;
    private ArrayList<Question> questionList;
    private static ArrayList<Exam> examList = new ArrayList<>();

    public Exam(String name, String difficulty, String examLength, ArrayList<Question> questionList) {
        this.name = name;
        this.difficulty = difficulty;
        this.examLength = examLength;
        this.questionList = questionList;

    }

    public  String getNameShort() {
        return name.substring(0, name.length() -4);
    }

    public String getDifficulty() {
        return difficulty;
    }

    public static ArrayList<Exam> getExamList() {
        return examList;
    }

    public static Exam getExam(String examName) {
        for (Exam exam : examList) {
            if (exam.name.equals(examName)) {
                return exam;
            }
        }
        return null;

    }

    public static List<Question> getShuffledExam(Exam exam) {
        Exam selectedExam = getExam(exam.name);
        int examLength = Integer.parseInt(selectedExam.examLength);
        ArrayList<Question> shuffledList = new ArrayList<>();

        int i = 0;
        for (Question question : selectedExam.questionList) {
            shuffledList.add(selectedExam.questionList.get(i++));
        }

        Collections.shuffle(shuffledList);

        return shuffledList.subList(0,  examLength);
    }

    public static void addExam(Exam exam) {
        examList.add(exam);
    }
}
