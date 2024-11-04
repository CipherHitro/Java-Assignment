package manager;

import tasks.Task;
import tasks.UrgentTask;
import exceptions.TaskNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import tasks.Task;

public class TaskManager implements TaskActions {
    private List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task added successfully!");
        saveTasksToFile();
    }

    
    @Override
    public void removeTask(int taskId) throws TaskNotFoundException {
        Task task = findTaskById(taskId);
        tasks.remove(task);
        System.out.println("Task removed successfully!");
        saveTasksToFile();
        if (isTaskListEmpty()) {
            cleanup();
        }
    }

    @Override
    public void markTaskComplete(int taskId) throws TaskNotFoundException {
        Task task = findTaskById(taskId);
        
        task.setCompleted(true);
        saveTasksToFile();
        // System.out.println("Task marked as completed!");
    }

    @Override
    public void displayTasks() { 
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        }

        List<Task> urgentTasks = new ArrayList<>();
        List<Task> regularTasks = new ArrayList<>();

        for (Task task : tasks) {
            if (task instanceof UrgentTask) {
                urgentTasks.add(task);
            } else {
                regularTasks.add(task);
            }
        }
        regularTasks.sort(Comparator.comparing(Task::getPriority).reversed());
        // Show urgent task first
        for (Task task : urgentTasks) {
            System.out.println(task);
        }
        // Show regular task by priority
        for (Task task : regularTasks) {
            System.out.println(task);
        }
    }

    public void searchTask(String name) throws TaskNotFoundException {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        boolean found = false;
        for (Task task : tasks) {
            if (task.getName().equalsIgnoreCase(name)) { 
                System.out.println(task);
                found = true;
            }
        }

        if (!found) {
            throw new TaskNotFoundException("Task with name '" + name + "' not found.");
        }
    }

    private void saveTasksToFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
        try (FileWriter writer = new FileWriter("tasks.json")) {
            gson.toJson(tasks, writer);
            System.out.println("Tasks saved to tasks.json");
        } catch (IOException e) {
            System.out.println("Error saving tasks to JSON file: " + e.getMessage());
        }
    }

    // Method to find the task by name.
    private Task findTaskById(int taskId) throws TaskNotFoundException {
        for (Task task : tasks) {
            if (task.getId() == taskId) {
                return task;
            }
        }
        throw new TaskNotFoundException("Task with Id " + taskId + " not found.");
    }

    public void cleanup() {
        File jsonFile = new File("tasks.json");
        if (jsonFile.delete()) {
            System.out.println("tasks.json deleted successfully.");
        } else {
            System.out.println("Failed to delete tasks.json.");
        }
    }

    public boolean isTaskListEmpty() {
        return tasks.isEmpty();
    }

}
