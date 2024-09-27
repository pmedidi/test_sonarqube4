import java.util.Date;

public class NormalTask implements Task{
    private String title;
    private String description;
    private Date dueDate;
    private String status;
    private int priority;
    @Override
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public NormalTask(String title, String description, Date dueDate, String status, int priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public String getStatus() {
        return this.status;
    }

    public int getPriority() {
        return this.priority;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
