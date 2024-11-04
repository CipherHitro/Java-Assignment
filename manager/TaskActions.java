package manager;
import tasks.Task;
import exceptions.TaskNotFoundException;

public interface TaskActions {
    void addTask(Task task);
    void removeTask(int taskId) throws TaskNotFoundException;
    void markTaskComplete(int taskId) throws TaskNotFoundException;
    void displayTasks();
    void searchTask(String name) throws TaskNotFoundException;
}
