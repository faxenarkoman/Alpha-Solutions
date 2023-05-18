package kea.dk.alpha_solutions.model;


import java.util.HashSet;
import java.util.Set;

public class Project {

    private int projectID;
    private String projectTitle;
    private String projectDescription;
    private String deadline;
    private int nrOfUsers;
    private int nrOfHours;
    private double projectPrice;
    private int hoursPerDay;


    private Set<Task> tasks = new HashSet<>();

    public Set<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(Set<Task> tasks)
    {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
        task.getProjects().add(this);
    }

    // Remove a task from the project
    public void removeTask(Task task) {
        tasks.remove(task);
        task.getProjects().remove(this);
    }

    public Project()
    {}

    public Project(int projectID, String projectTitle, String projectDescription, String deadline, int nrOfUsers, int nrOfHours, double projectPrice, int hoursPerDay)
    {
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.deadline = deadline;
        this.nrOfUsers = nrOfUsers;
        this.nrOfHours = nrOfHours;
        this.projectPrice = projectPrice;
        this.hoursPerDay = hoursPerDay;
    }


    public int getProjectID()
    {
        return projectID;
    }

    public void setProjectID(int projectID)
    {
        this.projectID = projectID;
    }

    public String getProjectTitle()
    {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle)
    {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription()
    {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription)
    {
        this.projectDescription = projectDescription;
    }

    public String getDeadline()
    {
        return deadline;
    }

    public void setDeadline(String deadline)
    {
        this.deadline = deadline;
    }

    public int getNrOfUsers()
    {
        return nrOfUsers;
    }

    public void setNrOfUsers(int nrOfUsers)
    {
        this.nrOfUsers = nrOfUsers;
    }

    public int getNrOfHours()
    {
        return nrOfHours;
    }

    public void setNrOfHours(int nrOfHours)
    {
        this.nrOfHours = nrOfHours;
    }


    public double getProjectPrice()
    {
        return projectPrice;
    }

    public void setProjectPrice(double projectPrice)
    {
        this.projectPrice = projectPrice;
    }

    public int getHoursPerDay()
    {
        return hoursPerDay;
    }

    public void setHoursPerDay(int hoursPerDay)
    {
        this.hoursPerDay = hoursPerDay;
    }

    @Override
    public String toString()
    {
        return "Project{" +
                "projectID=" + projectID +
                ", title='" + projectTitle + '\'' +
                ", description='" + projectDescription + '\'' +
                ", deadline=" + deadline +
                ", nrOfUsers=" + nrOfUsers +
                ", nrOfHours=" + nrOfHours +
                ", projectPrice=" + projectPrice +
                ", hoursPerDay=" + hoursPerDay +
                '}';
    }

}
