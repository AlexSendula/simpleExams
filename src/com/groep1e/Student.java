package com.groep1e;

import java.util.ArrayList;

public class Student {
    private String name;
    private String studentId;
    private static ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<String> completedExams = new ArrayList<>();

    public Student(String naam, String studentNummer) {
        this.name = naam;
        this.studentId = studentNummer;
    }

    public String getName() {
        return name;
    }

    public static Student getStudentById(String studentId) {
        try {
            for (Student student : studentList) {
                if (student.studentId.equals(studentId)) {
                    return student;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public String getStudentNummer() {
        return studentId;
    }

    public static ArrayList<Student> getStudentList() {
        return studentList;
    }

    public static void addStudent(Student student) {
        try {
            studentList.add(student);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void removeStudent(String studentId) {
        studentList.removeIf(student -> student.studentId.equals(studentId));
    }

    public void addCompletedExam(Exam exam) {
        if (!completedExams.contains(exam)) {
            completedExams.add(exam.getNameShort());
        }
    }

    public void removeCompletedExam(Exam exam) {
        this.completedExams.remove(exam);
    }

    public ArrayList<String> getCompletedExams() {
        return completedExams;
    }

    public static boolean isStudentIdValid(String studentId) {
        if (studentId.length() != 8) {
            return false;
        }

        return studentList.contains(Student.getStudentById(studentId));
    }

    public static Student getTopScoringStudent() {
        Student topScoringStudent = studentList.get(0);
        boolean noExamsCompleted = true;

        for (Student student : studentList) {
            if (student.completedExams.size() > 0) {
                noExamsCompleted = false;
            }

            if (student.completedExams.size() > topScoringStudent.completedExams.size()) {
                topScoringStudent = student;
            }
        }
        if (noExamsCompleted) {
            return null;
        }
        return topScoringStudent;
    }


}