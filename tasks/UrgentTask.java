package tasks;

public class UrgentTask extends Task {
    private Priority priority;
    public UrgentTask(String name, String dueDate) {
        super(name, dueDate);
        priority = Priority.HIGH;
    }

    @Override
    public String getPriority() {
        return "HIGH (Urgent)";
    }
}
