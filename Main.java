import manager.TaskManager;
import tasks.RegularTask;
import tasks.UrgentTask;
import tasks.Priority;
import exceptions.TaskNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n===== To-Do List Menu =====");
            System.out.println("1. Add Task");
            System.out.println("2. Remove Task");
            System.out.println("3. Display Tasks");
            System.out.println("4. Mark Task as Complete");
            System.out.println("5. Search Task");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();  
                
            } catch (InputMismatchException e) {
                System.out.println("Please Enter valid choice!!");
                scanner.nextLine();
                continue;
            }
            
            switch (choice) {
                
                case 1:
                    addTask(taskManager, scanner);
                    break;
                case 2:
                    removeTask(taskManager, scanner);
                    break;
                case 3:
                    taskManager.displayTasks();
                    break;
                case 4:
                    markTaskComplete(taskManager, scanner);
                    break;
                case 5:
                    searchTask(taskManager,scanner);
                    break;
                case 6:
                    taskManager.cleanup();
                    running = false;
                    System.out.println("Exited");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void addTask(TaskManager taskManager, Scanner scanner) {
        System.out.print("Enter task name: ");
        String name = scanner.nextLine();

        System.out.print("Enter due date (DD-MM-YYYY): ");
        String date = scanner.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        
        LocalDate dueDate = null;
        try {
            dueDate = LocalDate.parse(date, formatter);
        } 
        catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }

        LocalDate dateNow = LocalDate.now();
        System.out.println(dateNow);
        if (dueDate.isBefore(dateNow)) {
            System.out.println("Invalid date. The date cannot be in the past. Please enter today or a future date.");
            return;
        } 
        //Chanege date format : 
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");

        System.out.print("Is this an urgent task? (yes/no): ");
        String isUrgent = scanner.nextLine().trim().toLowerCase();

        if (isUrgent.equals("yes")) {
            UrgentTask urgentTask = new UrgentTask(name, dueDate.format(outputFormatter));
            taskManager.addTask(urgentTask);
        } 
        else{
            System.out.print("Enter priority (MEDIUM, LOW): ");
            String priorityStr = scanner.nextLine().toUpperCase();
            Priority priority;

            switch (priorityStr) {
                case "MEDIUM":
                    priority = Priority.MEDIUM;
                    break;
                case "LOW":
                    priority = Priority.LOW;
                    break;
                default:
                    System.out.println("Invalid priority. Setting to LOW.");
                    priority = Priority.LOW;
            }

            RegularTask regularTask = new RegularTask(name, dueDate.format(outputFormatter), priority);
            taskManager.addTask(regularTask);
        }
    }

    private static void removeTask(TaskManager taskManager, Scanner scanner) {
        if(taskManager.isTaskListEmpty()){
            System.out.println("No task available!");
            return;
        }
        System.out.print("Enter task Id to remove: ");
        int taskId = scanner.nextInt();

        try {
            taskManager.removeTask(taskId);
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void markTaskComplete(TaskManager taskManager, Scanner scanner) {
        if(taskManager.isTaskListEmpty()){
            System.out.println("No task available!");
            return;
        }
        System.out.print("Enter task Id to mark as complete: ");
        int taskId= scanner.nextInt();
        scanner.nextLine();

        try {
            taskManager.markTaskComplete(taskId);
        } 
        catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Do you also want to delete that task?(yes/no) :");
        String decision = scanner.nextLine();

        if(decision.equals("yes")){
            try {
                taskManager.removeTask(taskId);
            } 
            catch (TaskNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static void searchTask(TaskManager taskManager, Scanner scanner){
        if(taskManager.isTaskListEmpty()){
            System.out.println("No task available!");
            return;
        }
        System.out.println("Enter task name to search :");
        String name = scanner.nextLine();
        if(taskManager.isTaskListEmpty()){
            System.out.println("No tasks available to search!");
            return;
        }
        try {
            taskManager.searchTask(name);
            
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}