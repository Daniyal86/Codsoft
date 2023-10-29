import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class StudentRegistration {
    private String courseCode;
    private String title;
    private String description;
    private int capacity;
    private String schedule;
    private List<Student> students = new ArrayList<>();

    public StudentRegistration(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = schedule;
    }

    public String getStudentRegistrationCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public List<Student> getStudents() {
        return students;
    }

    public boolean registerStudent(Student student) {
        if (students.size() < capacity) {
            students.add(student);
            student.registerStudentRegistration(this);
            return true;
        } else {
            return false;
        }
    }

    public void removeStudent(Student student) {
        students.remove(student);
        student.removeStudentRegistration(this);
    }
}

class Student {
    private int studentID;
    private String name;
    private List<StudentRegistration> registeredStudentRegistrations = new ArrayList<>();

    public Student(int studentID, String name) {
        this.studentID = studentID;
        this.name = name;
    }

    public int getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<StudentRegistration> getRegisteredStudentRegistrations() {
        return registeredStudentRegistrations;
    }

    public void registerStudentRegistration(StudentRegistration course) {
        registeredStudentRegistrations.add(course);
    }

    public void removeStudentRegistration(StudentRegistration course) {
        registeredStudentRegistrations.remove(course);
    }
}

class StudentRegistrationRegistrationSystem {
    private List<StudentRegistration> courses = new ArrayList<>();
    private List<Student> students = new ArrayList();

    public void addStudentRegistration(StudentRegistration course) {
        courses.add(course);
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void displayAvailableStudentRegistrations() {
        System.out.println("Available StudentRegistrations:");
        for (StudentRegistration course : courses) {
            System.out.println("StudentRegistration Code: " + course.getStudentRegistrationCode());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Schedule: " + course.getSchedule());
            System.out.println("Available Slots: " + (course.getCapacity() - course.getStudents().size()));
            System.out.println();
        }
    }

    public static void main(String[] args) {
        StudentRegistrationRegistrationSystem registrationSystem = new StudentRegistrationRegistrationSystem();

        StudentRegistration course1 = new StudentRegistration("CSCI101", "Introduction to Programming",
                "Basic programming concepts", 30,
                "Mon/Wed 10:00 AM");
        StudentRegistration course2 = new StudentRegistration("MATH201", "Calculus I", "Single-variable calculus", 40,
                "Tue/Thu 2:00 PM");
        StudentRegistration course3 = new StudentRegistration("ENG101", "English Composition",
                "Writing and communication skills", 25,
                "Mon/Wed 1:00 PM");

        Student student1 = new Student(1, "John");
        Student student2 = new Student(2, "Alice");

        registrationSystem.addStudentRegistration(course1);
        registrationSystem.addStudentRegistration(course2);
        registrationSystem.addStudentRegistration(course3);

        registrationSystem.addStudent(student1);
        registrationSystem.addStudent(student2);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Options:");
            System.out.println("1. Display Available StudentRegistrations");
            System.out.println("2. Register for a StudentRegistration");
            System.out.println("3. Drop a StudentRegistration");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registrationSystem.displayAvailableStudentRegistrations();
                    break;
                case 2:
                    System.out.print("Enter your student ID: ");
                    int studentID = scanner.nextInt();
                    System.out.print("Enter the course code you want to register for: ");
                    scanner.nextLine(); // Consume newline
                    String courseCode = scanner.nextLine();
                    Student student = null;
                    for (Student s : registrationSystem.students) {
                        if (s.getStudentID() == studentID) {
                            student = s;
                            break;
                        }
                    }
                    if (student == null) {
                        System.out.println("Student not found.");
                    } else {
                        StudentRegistration selectedStudentRegistration = null;
                        for (StudentRegistration c : registrationSystem.courses) {
                            if (c.getStudentRegistrationCode().equals(courseCode)) {
                                selectedStudentRegistration = c;
                                break;
                            }
                        }
                        if (selectedStudentRegistration == null) {
                            System.out.println("StudentRegistration not found.");
                        } else {
                            boolean success = selectedStudentRegistration.registerStudent(student);
                            if (success) {
                                System.out.println("Registration successful.");
                            } else {
                                System.out.println("Registration failed. StudentRegistration is full.");
                            }
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter your student ID: ");
                    int studentIDToDrop = scanner.nextInt();
                    System.out.print("Enter the course code you want to drop: ");
                    scanner.nextLine(); // Consume newline
                    String courseCodeToDrop = scanner.nextLine();
                    Student studentToDrop = null;
                    for (Student s : registrationSystem.students) {
                        if (s.getStudentID() == studentIDToDrop) {
                            studentToDrop = s;
                            break;
                        }
                    }
                    if (studentToDrop == null) {
                        System.out.println("Student not found.");
                    } else {
                        StudentRegistration courseToDrop = null;
                        for (StudentRegistration c : registrationSystem.courses) {
                            if (c.getStudentRegistrationCode().equals(courseCodeToDrop)) {
                                courseToDrop = c;
                                break;
                            }
                        }
                        if (courseToDrop == null) {
                            System.out.println("StudentRegistration not found.");
                        } else {
                            studentToDrop.removeStudentRegistration(courseToDrop);
                            courseToDrop.removeStudent(studentToDrop);
                            System.out.println("StudentRegistration dropped successfully.");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Thank you for using the StudentRegistration Registration System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }
    }
}
