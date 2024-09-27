import java.util.*;

public class Project {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private PriorityQueue<TeamMember> members;
    private PriorityQueue<Task> tasks;

    public Project(String name, String description, Date startDate, Date endDate) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = new PriorityQueue<TeamMember>(new TeamMemberComparator());
        this.tasks = new PriorityQueue<Task>(new TaskComparator());
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void removeTask(Task task) {
        for (Task testTask : tasks) {
            if (testTask == task) {
                tasks.remove(testTask);
            }
        }
    }

    public Task getNextTask() {
        return tasks.peek();
    }

    public Task completeNextTask() {
        return tasks.poll();
    }

    public TeamMember getLeader() {
        return members.peek();
    }

    public TeamMember removeLeader() {
        return members.poll();
    }

    public void addMember(TeamMember member) {
        members.add(member);
    }

    public void removeMember(TeamMember member) {
        for (TeamMember testMember : members) {
            if (testMember == member) {
                members.remove(testMember);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


}
