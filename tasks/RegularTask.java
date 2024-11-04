package tasks;

public class RegularTask extends Task {
    private Priority priority;

    public RegularTask(String name, String dueDate, Priority priority) {
        super(name, dueDate);
        this.priority = priority;
    }

    @Override 
    public String getPriority() {
        return priority.name();
    }
}
