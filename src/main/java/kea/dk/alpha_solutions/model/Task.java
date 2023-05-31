package kea.dk.alpha_solutions.model;

import java.util.HashSet;
import java.util.Set;

public class Task {
    private int taskId;
    private String taskName;
    private int taskNrOfHours;
    private int taskNrOfUsers;
    private String taskDescription;
    private String taskDeadline;
    private int taskHoursPrDay;

    private int projectId;

    private boolean completed;

    private Set<Project> projects = new HashSet<>();

    public Task(int taskId, String taskName, int taskNrOfHours, int taskNrOfUsers, String taskDescription,
                String taskDeadline, int taskHoursPrDay, boolean completed, int projectId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskNrOfHours = taskNrOfHours;
        this.taskNrOfUsers = taskNrOfUsers;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
        this.taskHoursPrDay = taskHoursPrDay;
        this.completed = completed;
        this.projectId = projectId;
    }



    public Task()
    {}

    public int getProjectId()
    {
        return projectId;
    }

    public void setProjectId(int projectId)
    {
        this.projectId = projectId;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        projects.add(project);
        project.getTasks().add(this);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.getTasks().remove(this);
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTaskNrOfHours() {
        return taskNrOfHours;
    }

    public void setTaskNrOfHours(int taskNrOfHours) {
        this.taskNrOfHours = taskNrOfHours;
    }

    public int getTaskNrOfUsers() {
        return taskNrOfUsers;
    }

    public void setTaskNrOfUsers(int taskNrOfUsers) {
        this.taskNrOfUsers = taskNrOfUsers;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public int getTaskHoursPrDay() {
        return taskHoursPrDay;
    }

    public void setTaskHoursPrDay(int taskHoursPrDay) {
        this.taskHoursPrDay = taskHoursPrDay;
    }

    public Boolean setCompleted(boolean completed) {
    	this.completed = completed;
        return false;
    }

    public Boolean getCompleted() {
    	return this.completed;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", taskName='" + taskName + '\'' +
                ", taskNrOfHours=" + taskNrOfHours +
                ", taskNrOfUsers=" + taskNrOfUsers +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskDeadline='" + taskDeadline + '\'' +
                ", taskHoursPrDay=" + taskHoursPrDay +
                '}';
    }
}
