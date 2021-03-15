package com.groep1e;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.gson.Gson;

public class Main {
    private static final String examPath = "../simpleExams/src/com/groep1e/Exams/";

    public static void main(String[] args) {

        initializeStudents();
        initializeExams();

        menuData();
    }

    public static void menuData() {

        System.out.printf("\n");
        System.out.println("1) List of exams");
        System.out.println("2) List of students");
        System.out.println("3) Create new student");
        System.out.println("4) Delete student");
        System.out.println("5) Take exam");
        System.out.println("6) Exams completed by student");
        System.out.println("7) Top scoring student");
        System.out.println("8) Create new exams");
        System.out.println("9) Delete exams");
        System.out.println("0) Exit");
        System.out.println("Please choose: ");

        int selection;

        try {
            Scanner scanner = new Scanner(System.in);
            selection = scanner.nextInt();
        } catch (Exception e) {
            clearScrean();
            menuData();
            return;
        }

        if (selection >= 0 && selection <= 9) {
            switch (selection) {
                case 0:
                    clearScrean();
                    closeApplication();
                    break;
                case 1:
                    clearScrean();
                    listExams();
                    break;
                case 2:
                    clearScrean();
                    listStudents();
                    break;
                case 3:
                    clearScrean();
                    createStudent();
                    break;
                case 4:
                    clearScrean();
                    removeStudent();
                    break;
                case 5:
                    clearScrean();
                    takeExam();
                    break;
                case 6:
                    clearScrean();
                    studentCompletedExams();
                    break;
                case 7:
                    clearScrean();
                    topScoringStudent();
                    break;
                case 8:
                    clearScrean();
                    createExams();
                    break;
                case 9:
                    clearScrean();
                    deleteExams();
                    break;
            }
        } else {
            clearScrean();
            menuData();
        }
    }

    public static void closeApplication() {
        System.out.println("Thank you for trying out my program! - Alexandar Sendula");
        storeStudents();
        System.exit(0);
    }

    public static void listExams() {
        for (Exam exam : Exam.getExamList()) {
            System.out.println(exam.getNameShort());
        }

        menuData();
    }

    public static void createStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name, StudentId");
        String[] input = scanner.nextLine().split(", ");

        try {
            if (!Student.isStudentIdValid(input[1])) {
                Student student = new Student(input[0], input[1]);

                Student.addStudent(student);
                System.out.printf("Student %s has been created.\n", student.getName());
            } else {
                System.out.println("Invalid student id, please try again.");
            }
        } catch (Exception e) {
            System.out.println("Invalid entry, please try again.");
        }

        menuData();
    }

    public static void listStudents() {
        for(Student student : Student.getStudentList()) {
            System.out.printf("%s, %s\n", student.getName(), student.getStudentNummer());
        }

        menuData();
    }

    public static void removeStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please provide a valid student id.");
        String studentId = scanner.nextLine();
        if (!Student.isStudentIdValid(studentId)) {
            clearScrean();
            System.out.println("Invalid student id, please try again.");
            menuData();
        }

        System.out.println("Please confirm: y/n");
        if (scanner.nextLine().equals("y")) {
            Student.removeStudent(studentId);
            clearScrean();
            System.out.println("Student has been deleted.");
            menuData();
        } else {
            clearScrean();
            menuData();
        }
    }

    public static void takeExam()  {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is your student id?");
        String studentId = scanner.nextLine();
        Student student;

        if (Student.isStudentIdValid(studentId)) {
            student = Student.getStudentById(studentId);
        } else {
            System.out.println("Invalid student id, please try again.");
            menuData();
            return;
        }

        System.out.println("Please select an exam.");
        for (Exam exam : Exam.getExamList()) {
            System.out.println(exam.getNameShort());
        }

        try {
            String selection = scanner.nextLine() + ".txt";
            Exam exam = Exam.getExam(selection);
            int totalCorrect = 0;
            clearScrean();

            for (Question question : Exam.getShuffledExam(exam)) {
                System.out.println(question.getQuestion());
                String userInput = scanner.nextLine();
                if (userInput.equalsIgnoreCase(question.getAnswer())) {
                    clearScrean();
                    System.out.println("Correct!");
                    totalCorrect++;
                } else {
                    clearScrean();
                    System.out.printf("Question: %s\nYour answer: %s\n", question.getQuestion(), userInput);
                    System.out.printf("Incorrect! The right answer should be: %s\n", question.getAnswer());
                }
            }

            clearScrean();
            double score = (100.00 / Exam.getShuffledExam(exam).size()) * totalCorrect;
            if (score >= Double.parseDouble(exam.getDifficulty())) {
                student.addCompletedExam(exam);
                System.out.println("Congratulations! You have completed the exam.");
            } else {
                System.out.println("You have failed the exam, please try again.");
            }

        } catch (Exception e) {
            clearScrean();
            System.out.println("Invalid exam, please try again.");
        }
        menuData();
    }

    public static void studentCompletedExams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please provide a valid student id.");
        String studentId = scanner.nextLine();
        if (!Student.isStudentIdValid(studentId)) {
            clearScrean();
            menuData();
            return;
        }

        Student student = Student.getStudentById(studentId);
        clearScrean();

        if (student.getCompletedExams().size() == 0) {
            System.out.printf("%s has not yet completed any exams.\n", student.getName());
        } else {
            System.out.printf("%s has completed the following exams:\n", student.getName());
            for (String exam : student.getCompletedExams()) {
                System.out.println(exam);
            }
        }
        menuData();
    }

    public static void topScoringStudent() {
        Student student = Student.getTopScoringStudent();

        if (student == null) {
            System.out.println("No exams have been completed so far.");
        } else {
            System.out.printf("%s has completed most exams. Number of exams completed: %s\n", student.getName(), student.getCompletedExams().size());
        }
        menuData();
    }

    public static void  createExams() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a name.");
        String examName = scanner.nextLine() + ".txt";
        System.out.println("Please confirm.");
        if (!examName.equals(scanner.nextLine() + ".txt")) {
            clearScrean();
            System.out.println("Names do not match, please try again.");
            menuData();
            return;
        }

        File file = new File(examPath + examName);
        try {
            if (file.exists()) {
                clearScrean();
                System.out.println("This exam already exists, please try again.");
                menuData();
            }

            System.out.println("What is the difficulty? (e.g. 62.5)");
            Double difficultyDouble = scanner.nextDouble();
            String difficulty = Double.toString(difficultyDouble);
            System.out.println("How many questions will the exam have?");
            int examLengthInt = scanner.nextInt();
            String examLength = Integer.toString(examLengthInt);

            FileWriter writer = new FileWriter(examPath + examName);
            writer.write(difficulty + ",");
            writer.write(examLength + ",");
            scanner.nextLine();

            Question.resetQuestonList();

            for (int i = 0; i < Integer.parseInt(examLength); i++) {
                System.out.printf("Question %d)\n", i+1);
                String question = scanner.nextLine();
                writer.write(question + ",");
                System.out.printf("Answer %d)\n", i+1);
                String answer = scanner.nextLine();
                writer.write(answer + ",");

                Question newQuestion = new Question(question, answer);
                Question.addQuestion(newQuestion);
            }

            Exam.addExam(new Exam(examName, difficulty, examLength, Question.getQuestionList()));

            writer.close();
            clearScrean();
            System.out.println("Exam succesfully created!");
            menuData();
        } catch (Exception e) {

            clearScrean();
            System.out.println("Invalid entry, please try again.");
            menuData();
        }
    }

    public static void deleteExams() {
        Scanner scanner = new Scanner(System.in);

        for (Exam exam : Exam.getExamList()) {
            System.out.println(exam.getNameShort());
        }
        System.out.println("Which exam would you like to delete?.");
        String examName = scanner.nextLine();
        if (Exam.getExam(examName + ".txt") == null) {
            clearScrean();
            System.out.println("Exam does not exist, please try again.");
            menuData();
        }
        System.out.println("Please confirm. y/n");
        if (scanner.nextLine().equals("y")) {
            Exam exam = Exam.getExam(examName + ".txt");

            try {
                System.gc();
                if (Files.deleteIfExists(Paths.get(examPath + examName + ".txt"))) {
                    Exam.removeExam(exam);

                    for (Student student : Student.getStudentList()) {
                        if (student.getCompletedExams().contains(exam)) {
                            student.removeCompletedExam(exam);
                        }
                    }
                    storeStudents();
                    clearScrean();
                    System.out.printf("%s has been deleted", examName);
                    menuData();
                } else {
                    clearScrean();
                    System.out.println("error");
                    menuData();
                }
            } catch (Exception e) {
                System.out.println("Error, please try again.");
                clearScrean();
                menuData();
            }
        }
    }

    public static void storeStudents() {
        try {
            File file = new File("../simpleExams/src/com/groep1e/students.txt");
            System.gc();
            if (Files.deleteIfExists(Paths.get(file.getAbsolutePath())) && !file.createNewFile()) {
                System.out.println("ERROR: Data could not be stored.");
            }

            FileWriter writer = new FileWriter(file.getAbsolutePath());

            for (Student student : Student.getStudentList()) {
                 Gson gson = new Gson();
                 String json = gson.toJson(student);
                 writer.write(json + "\n");
            }
            writer.flush();
            writer.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void initializeStudents() {
        try {
            Scanner scanner = new Scanner(new File("../simpleExams/src/com/groep1e/students.txt"));
            Gson gson = new Gson();

            while (scanner.hasNextLine()) {
                Student.addStudent(gson.fromJson(scanner.nextLine(), Student.class));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void initializeExams() {
        try {
            File folder = new File(examPath);
            for (File f : folder.listFiles()) {
                Scanner scanner = new Scanner(new File(examPath + f.getName()));
                if (scanner.hasNext()) {
                    scanner.useDelimiter(",");
                    String difficulty = scanner.next();
                    String examLength = scanner.next();

                    Exam.addExam(new Exam(f.getName(), difficulty, examLength, initializeQuestions(f.getName())));
                } else {
                    f.deleteOnExit();
                }

            }
        } catch (Exception e) {
            clearScrean();
            System.out.println(e);
            System.out.println("Failed to initialize exams. Closing program.");
            closeApplication();
        }
    }

    public static ArrayList<Question> initializeQuestions(String examName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(examPath + examName));
        scanner.useDelimiter(",");
        Question.resetQuestonList();
        scanner.nextLine();
        while (scanner.hasNext() || scanner.hasNextInt()) {
            String question = scanner.next();
            String answer = scanner.next();

            Question newQuestion = new Question(question, answer);
            Question.addQuestion(newQuestion);
        }
        scanner.close();
        return Question.getQuestionList();
    }

    public static void clearScrean() {
        for (int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }
}