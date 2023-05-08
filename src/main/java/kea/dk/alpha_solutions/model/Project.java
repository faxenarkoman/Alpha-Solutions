package kea.dk.alpha_solutions.model;

import java.util.ArrayList;
import java.util.Date;

public class Project {
    private int projectID;
    private String title;
    private String description;
    private String deadline;
    private int nrOfUsers;
    private int nrOfHours;
    private ArrayList<Task> tasks = new ArrayList<>();
    private double projectPrice;
    private int hoursPerDay;


    public Project(int projectID, String title, String description, String deadline, int nrOfUsers, int nrOfHours, double projectPrice, int hoursPerDay)
    {
        this.projectID = projectID;
        this.title = title;
        this.description = description;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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

    public ArrayList<Task> getTasks()
    {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks)
    {
        this.tasks = tasks;
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
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", nrOfUsers=" + nrOfUsers +
                ", nrOfHours=" + nrOfHours +
                ", tasks=" + tasks +
                ", projectPrice=" + projectPrice +
                ", hoursPerDay=" + hoursPerDay +
                '}';
    }
}
