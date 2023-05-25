package kea.dk.alpha_solutions.model;

import java.util.ArrayList;

public class Task
{
    private int taskId;
    private String taskName;
    private int taskNrOfHours;
    private int taskNrOfUsers;
    private String taskDescription;

    private String taskDeadline;
    private int taskHoursPrDay;


    public Task(int taskId, String taskName, int taskNrOfHours, int taskNrOfUsers, String taskDescription, String taskDeadline, int taskHoursPrDay)
    {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskNrOfHours = taskNrOfHours;
        this.taskNrOfUsers = taskNrOfUsers;
        this.taskDescription = taskDescription;
        this.taskDeadline = taskDeadline;
        this.taskHoursPrDay = taskHoursPrDay;
    }

    public Task()
    {}

    public int getTaskId()
    {
        return taskId;
    }

    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskName()
    {
        return taskName;
    }

    public void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }

    public int getTaskNrOfHours()
    {
        return taskNrOfHours;
    }

    public void setTaskNrOfHours(int taskNrOfHours)
    {
        this.taskNrOfHours = taskNrOfHours;
    }

    public int getTaskNrOfUsers()
    {
        return taskNrOfUsers;
    }

    public void setTaskNrOfUsers(int taskNrOfUsers)
    {
        this.taskNrOfUsers = taskNrOfUsers;
    }

    public String getTaskDescription()
    {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public String getTaskDeadline()
    {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline)
    {
        this.taskDeadline = taskDeadline;
    }

    public int getTaskHoursPrDay()
    {
        return taskHoursPrDay;
    }

    public void setTaskHoursPrDay(int taskHoursPrDay)
    {
        this.taskHoursPrDay = taskHoursPrDay;
    }


}