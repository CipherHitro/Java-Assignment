package tasks;

public abstract class Task {
    private static int idCounter = 0;

    protected int id;
    protected String name;
    protected String dueDate;
    protected boolean isCompleted;

    public Task(String name, String dueDate) {
        this.id = ++idCounter;
        this.name = name;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    // Change date format : 
    
    public abstract String getPriority();

    public void setCompleted(boolean completed) {
        if(this.isCompleted == true){
            System.out.println("task already marked as completed!");
        }
        else{
            this.isCompleted = completed;
            System.out.println("Task marked as completed!");
        }
    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Id :"+ id +", Task: " + name + ", Due: " + dueDate + ", Priority: " + getPriority() + ", Completed: " + isCompleted;
    }
}
