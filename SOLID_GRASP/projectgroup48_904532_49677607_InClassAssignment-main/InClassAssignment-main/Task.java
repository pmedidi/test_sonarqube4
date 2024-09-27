import java.util.Date;

public interface Task {
    public void updateStatus(String newStatus);
    public String getTitle();
    public String getDescription();
    public Date getDueDate();
    public String getStatus();
    public int getPriority();
    public void setTitle(String title);
    public void setDescription(String description);
    public void setDueDate(Date dueDate);
    public void setStatus(String status);
    public void setPriority(int priority);
}
