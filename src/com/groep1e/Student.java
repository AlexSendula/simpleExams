package com.groep1e;

import java.util.ArrayList;

public class Student {
    private String name;
    private String studentId;
    private static ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Exam> completedExams = new ArrayList<>();

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
            completedExams.add(exam);
        }
    }

    public ArrayList<Exam> getCompletedExams() {
        return completedExams;
    }

    public static boolean isStudentIdValid(String studentId, boolean mustExist) {
//        Boolean mustExist: TRUE -> finds if studentId exists
//        Boolean mustExist: FALSE -> finds if studentId does not exist

        if (studentId.length() != 8) {
            return false;
        }

        if (mustExist) {
            for (Student student : studentList) {
                if (student.studentId.equals(studentId)) {
                    return true;
                }
            }
        } else {
            for (Student student : studentList) {
                if (student.studentId.equals(studentId)) {
                    return false;
                }
            }
        }

        return true;
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