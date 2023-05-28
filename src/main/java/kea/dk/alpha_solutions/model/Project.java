package kea.dk.alpha_solutions.model;

import java.util.HashSet;
import java.util.Set;

public class Project {
    private int projectID;
    private String projectTitle;
    private String projectDescription;
    private String deadline;

    private int hoursPerDay;

    private Set<Task> tasks = new HashSet<>();

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }



    public void addTask(Task task) {
        tasks.add(task);
        task.getProjects().add(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.getProjects().remove(this);
    }

    // Constructors, getters, and setters

    public Project() {
    }

    public Project(int projectID, String projectTitle, String deadline, int hoursPerDay, String projectDescription) {
        this.projectID = projectID;
        this.projectTitle = projectTitle;
        this.projectDescription = projectDescription;
        this.deadline = deadline;
        this.hoursPerDay = hoursPerDay;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getHoursPerDay() {
        return hoursPerDay;
    }

    public void setHoursPerDay(int hoursPerDay) {
        this.hoursPerDay = hoursPerDay;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectID=" + projectID +
                ", projectTitle='" + projectTitle + '\'' +
                ", deadline='" + deadline + '\'' +
                ", projectDescription='" + projectDescription + '\'' +
                ", hoursPerDay=" + hoursPerDay +
                '}';
    }


}
