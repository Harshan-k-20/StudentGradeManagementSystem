import java.util.*;

public class Main {

    static class Student {
        String name;
        int marks;

        Student(String name, int marks) {
            this.name = name;
            this.marks = marks;
        }

        String getGrade() {
            if (marks >= 90) return "A";
            else if (marks >= 75) return "B";
            else if (marks >= 50) return "C";
            else return "F";
        }

        public String toString() {
            return name + " - Marks: " + marks + ", Grade: " + getGrade();
        }
    }

    static List<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== Student Grade System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> searchStudent();
                case 4 -> deleteStudent();
                case 5 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }

    static void addStudent() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter marks: ");
        int marks = sc.nextInt();
        students.add(new Student(name, marks));
        System.out.println("✅ Student added.");
    }

    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        System.out.println("\n--- Student List ---");
        for (Student s : students) {
            System.out.println(s);
        }
    }

    static void searchStudent() {
        System.out.print("Enter name to search: ");
        String name = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Student s : students) {
            if (s.name.toLowerCase().contains(name)) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) System.out.println("❌ Student not found.");
    }

    static void deleteStudent() {
        System.out.print("Enter name to delete: ");
        String name = sc.nextLine().toLowerCase();
        boolean removed = students.removeIf(s -> s.name.toLowerCase().equals(name));
        if (removed) System.out.println("🗑️ Student deleted.");
        else System.out.println("❌ Student not found.");
    }
}
