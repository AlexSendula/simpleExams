package com.groep1e;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        MenuData();

    }

    public static void MenuData() {

        System.out.printf("\n");
        System.out.println("1) Lijst met examens");
        System.out.println("2) Lijst met studenten");
        System.out.println("3) Nieuwe student inschrijven");
        System.out.println("4) Student verwijderen");
        System.out.println("5) Examen afnemen");
        System.out.println("6) Welke examens heeft student gehaald?");
        System.out.println("7) Welke student heeft de meeste examens gehaald?");
        System.out.println("0) Exit");
        System.out.println("Uw keuze: ");

        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();

        if (selection >= 0 && selection <= 8) {
            switch (selection) {
                case 0:
                    closeApplication();
                    break;
                case 1:
                    listExams();
                    break;
                case 2:
                    allStudents();
                    break;
                case 3:
                    createStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    startExam();
                    break;
                case 6:
                    examensBehaald();
                    break;
                case 7:
                    System.out.println(Student.meestBehaaldeExamens().getNaam());
                    break;
            }
        }
    }

    public static void closeApplication() {
        System.exit(0);
    }

    public static void createStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Naam:");
        String naam = scanner.nextLine();
        System.out.println("Studentnummer:");
        int studentNummer = scanner.nextInt();

        Student student = new Student(naam, studentNummer);

        Student.addStudent(student);
        System.out.printf("Student %s is aangemaakt \n", student.getNaam());

        MenuData();

    }

    public static void allStudents() {
        for(Student student : Student.getStudentList()) {
            System.out.printf("%s, %d\n", student.getNaam(), student.getStudentNummer());
        }

        MenuData();
    }

    public static void removeStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Studentnummer:");
        Student.removeStudent(scanner.nextInt());
        System.out.println("Student is verwijderd");

        MenuData();
    }

    public static void examensBehaald() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Studentnummer:");
        Student student = Student.getStudentById(scanner.nextInt());
        ArrayList<String> behaaldeExamens = student.getBehaaldeExamens();
        System.out.printf("%s heeft de volgende examens behaald:\n", student.getNaam());
        for (String examen : behaaldeExamens) {
            System.out.println(examen);
        }
        System.out.println("\n");
        MenuData();
    }

    public static void listExams() {
        File folder = new File("../simpleExams/src/com/groep1e/Exams");
        for (File f : folder.listFiles()) {
            System.out.println(f.getName());
        }

        MenuData();
    }

    public static void startExam() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wat is je studentnummer?");
        Student student = Student.getStudentById(scanner.nextInt());
        scanner.nextLine();

        System.out.println("Welke examen wilt u afnemen?");
        File folder = new File("../simpleExams/src/com/groep1e/Exams");
        for (File f : folder.listFiles()) {
            System.out.println(f.getName());
        }

        try {
            String gekozenExamen = scanner.nextLine();
            createQuestions(gekozenExamen);
            int fouten = 0;

            for (Question question : Question.getQuestionslist()) {
                System.out.println(question.getVraag());
                if (scanner.next().equals(question.getAntwoord())) {
                    System.out.println("Goed!");
                } else {
                    System.out.println("Fout!");
                    fouten++;
                }
            }

            if (fouten > 0) {
                System.out.printf("Je hebt het examen niet gehaald, aantal fout: %d", fouten);
            } else {
                student.examenBehaald(gekozenExamen);
                System.out.println("Je hebt het examen behaald!");
            }

            System.out.printf("Het examen is afgerond, je hebt %d fout!\n", fouten);
            MenuData();

        } catch (Exception e) {
            System.out.println("Probeer het opnieuw");
        }

        MenuData();

    }

    public static void createQuestions(String exam) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("../simpleExams/src/com/groep1e/Exams/" + exam));
        scanner.useDelimiter(",");
        int i = 0;
        String vraag = null;
        String antwoord = null;
        while (scanner.hasNext()) {
            if (i == 0) {
                vraag = scanner.next();
                i++;
            }
            if(i == 1) {
                antwoord = scanner.next();
                i--;
            }
            Question question = new Question(vraag, antwoord);
            Question.addQuestion(question);
        }
    }
}