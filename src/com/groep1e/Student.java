package com.groep1e;

import java.util.ArrayList;

public class Student {
    private String naam;
    private int studentNummer;
    private static ArrayList<Student> studentList = new ArrayList<Student>();
    private ArrayList<String> behaaldeExamens = new ArrayList<String>();

    public Student(String naam, int studentNummer) {
        this.naam = naam;
        this.studentNummer = studentNummer;
    }

    public String getNaam() {
        return this.naam;
    }

    public int getStudentNummer() {
        return studentNummer;
    }

    public static ArrayList<Student> getStudentList() {
        return studentList;
    }

    public static void addStudent(Student student) {
        studentList.add(student);
        System.out.println("Student succefully added to collections");
    }

    public static void removeStudent(int studentNummer) {
        studentList.removeIf(student -> student.studentNummer == studentNummer);
    }

    public void examenBehaald(String behaaldeExamen) {
        this.behaaldeExamens.add(behaaldeExamen);
    }

    public ArrayList<String> getBehaaldeExamens() {
        return behaaldeExamens;
    }

    public static Student getStudentById(int studentNummer) {
        for (Student student : studentList) {
            if (student.studentNummer == studentNummer) {
                return student;
            }
        }
        return null;
    }

    public static Student meestBehaaldeExamens() {
        Student bestStudent = studentList.get(0);
        for (Student student : studentList) {
            if (student.getBehaaldeExamens().size() > bestStudent.getBehaaldeExamens().size()) {
                bestStudent = student;
            }
        }
        return bestStudent;
    }
}