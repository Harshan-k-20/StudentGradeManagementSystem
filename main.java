import java.io.*;
import java.util.*;

public class Main {
    static class Task {
        String name;
        boolean isCompleted;

        Task(String name) {
            this.name = name;
            this.isCompleted = false;
        }

        void markCompleted() {
            isCompleted = true;
        }

        @Override
        public String toString() {
            return (isCompleted ? "[Completed] " : "[Pending] ") + name;
        }
    }

    private static final String TASK_FILE = "tasks.txt";

    public static void main(String[] args) {
        ArrayList<Task> tasks = loadTasks();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n----- Task Manager -----");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Remove Task");
            System.out.println("5. Edit Task");
            System.out.println("6. Search Task");
            System.out.println("7. Sort Tasks");
            System.out.println("8. Save & Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            switch (option) {
                case 1:
                    System.out.print("Enter task name: ");
                    String taskName = scanner.nextLine();
                    tasks.add(new Task(taskName));
                    System.out.println("Task added!");
                    break;

                case 2:
                    System.out.println("----- Task List -----");
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks to display.");
                    } else {
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter task number to mark as completed: ");
                    int taskToComplete = scanner.nextInt() - 1;
                    if (taskToComplete >= 0 && taskToComplete < tasks.size()) {
                        tasks.get(taskToComplete).markCompleted();
                        System.out.println("Task marked as completed.");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 4:
                    System.out.print("Enter task number to remove: ");
                    int taskToRemove = scanner.nextInt() - 1;
                    if (taskToRemove >= 0 && taskToRemove < tasks.size()) {
                        tasks.remove(taskToRemove);
                        System.out.println("Task removed.");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 5:
                    System.out.print("Enter task number to edit: ");
                    int taskToEdit = scanner.nextInt() - 1;
                    scanner.nextLine(); // consume the newline
                    if (taskToEdit >= 0 && taskToEdit < tasks.size()) {
                        System.out.print("Enter new task name: ");
                        String newTaskName = scanner.nextLine();
                        tasks.get(taskToEdit).name = newTaskName;
                        System.out.println("Task updated.");
                    } else {
                        System.out.println("Invalid task number.");
                    }
                    break;

                case 6:
                    System.out.print("Enter task name to search: ");
                    String searchQuery = scanner.nextLine();
                    System.out.println("----- Search Results -----");
                    boolean found = false;
                    for (int i = 0; i < tasks.size(); i++) {
                        if (tasks.get(i).name.toLowerCase().contains(searchQuery.toLowerCase())) {
                            System.out.println((i + 1) + ". " + tasks.get(i));
                            found = true;
                        }
                    }
                    if (!found) {
                        System.out.println("No tasks found matching your search.");
                    }
                    break;

                case 7:
                    System.out.println("----- Sort Tasks -----");
                    System.out.println("1. Sort by Status (Pending/Completed)");
                    System.out.println("2. Sort by Name (Alphabetically)");
                    System.out.print("Choose sorting option: ");
                    int sortOption = scanner.nextInt();
                    scanner.nextLine(); // consume the newline

                    if (sortOption == 1) {
                        tasks.sort(Comparator.comparing(task -> task.isCompleted));
                    } else if (sortOption == 2) {
                        tasks.sort(Comparator.comparing(task -> task.name));
                    } else {
                        System.out.println("Invalid sorting option.");
                    }
                    break;

                case 8:
                    saveTasks(tasks);
                    System.out.println("Tasks saved. Exiting Task Manager. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TASK_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] taskData = line.split(";");
                String taskName = taskData[0];
                boolean isCompleted = Boolean.parseBoolean(taskData[1]);
                Task task = new Task(taskName);
                if (isCompleted) {
                    task.markCompleted();
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("No saved tasks found.");
        }
        return tasks;
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK_FILE))) {
            for (Task task : tasks) {
                writer.write(task.name + ";" + task.isCompleted);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }
}
