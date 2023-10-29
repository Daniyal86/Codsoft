import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean removeStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                students.remove(student);
                return true;
            }
        }
        return false;
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }
}

public class StudentManagementSystemApp {
    public static void main(String[] args) {
        StudentManagementSystem system = new StudentManagementSystem();
        loadStudentData(system);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add a Student");
            System.out.println("2. Remove a Student");
            System.out.println("3. Search for a Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(system, scanner);
                    break;
                case 2:
                    removeStudent(system, scanner);
                    break;
                case 3:
                    searchStudent(system, scanner);
                    break;
                case 4:
                    displayAllStudents(system);
                    break;
                case 5:
                    saveStudentData(system);
                    System.out.println("Thank you for using the Student Management System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }

    private static void addStudent(StudentManagementSystem system, Scanner scanner) {
        System.out.print("Enter the student's name: ");
        String name = scanner.nextLine();

        System.out.print("Enter the student's roll number: ");
        int rollNumber = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter the student's grade: ");
        String grade = scanner.nextLine();

        if (name.isEmpty() || grade.isEmpty()) {
            System.out.println("Name and grade are required. Student not added.");
        } else {
            Student student = new Student(name, rollNumber, grade);
            system.addStudent(student);
            System.out.println("Student added successfully.");
        }
    }

    private static void removeStudent(StudentManagementSystem system, Scanner scanner) {
        System.out.print("Enter the roll number of the student to remove: ");
        int rollNumber = scanner.nextInt();
        if (system.removeStudent(rollNumber)) {
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found. Removal failed.");
        }
    }

    private static void searchStudent(StudentManagementSystem system, Scanner scanner) {
        System.out.print("Enter the roll number of the student to search: ");
        int rollNumber = scanner.nextInt();
        Student student = system.searchStudent(rollNumber);
        if (student != null) {
            System.out.println("Student found: " + student);
        } else {
            System.out.println("Student not found.");
        }
    }

    private static void displayAllStudents(StudentManagementSystem system) {
        List<Student> students = system.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students in the system.");
        } else {
            System.out.println("All Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    private static void loadStudentData(StudentManagementSystem system) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("student_data.ser"))) {
            List<Student> students = (List<Student>) inputStream.readObject();
            system.getAllStudents().addAll(students);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Student data file not found or corrupted.");
        }
    }

    private static void saveStudentData(StudentManagementSystem system) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("student_data.ser"))) {
            outputStream.writeObject(system.getAllStudents());
        } catch (IOException e) {
            System.out.println("Error saving student data.");
        }
    }
}
