package kea.dk.alpha_solutions.model;

import java.util.ArrayList;

public class Task {
    private int taskId;
    private int projectId;
    private String name;
    private String taskDescription;
    private String startDate;
    private String deadline;
    private int hours;
    private ArrayList<Subtask> subtasks = new ArrayList<Subtask>();


    public Task(int taskId, int projectId, String name, String taskDescription, String startDate, String deadline, int hours)
    {
        this.taskId = taskId;
        this.projectId = projectId;
        this.name = name;
        this.taskDescription = taskDescription;
        this.startDate = startDate;
        this.deadline = deadline;
        this.hours = hours;
    }

    public int getTaskId()
    {
        return taskId;
    }

    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    public int getProjectId()
    {
        return projectId;
    }

    public void setProjectId(int projectId)
    {
        this.projectId = projectId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getDeadline()
    {
        return deadline;
    }

    public void setDeadline(String deadline)
    {
        this.deadline = deadline;
    }

    public int getHours()
    {
        return hours;
    }

    public void setHours(int hours)
    {
        this.hours = hours;
    }

    @Override
    public String toString()
    {
        return "Task{" +
                "taskId=" + taskId +
                ", projectId=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + taskDescription + '\'' +
                ", startDate='" + startDate + '\'' +
                ", deadline='" + deadline + '\'' +
                ", hours=" + hours +
                ", subtasks=" + subtasks +
                '}';
    }
}
