package kea.dk.alpha_solutions.model;

import java.util.Date;

public class Subtask{
    private int subtaskID;
    private String name;
    private String description;
    private int taskId;
    private int userId;
    private Date start;
    private Date deadline;

    public Subtask(int subtaskID, String name, String description, int taskId, int userId, Date start, Date deadline)
    {
        this.subtaskID = subtaskID;
        this.name = name;
        this.description = description;
        this.taskId = taskId;
        this.userId = userId;
        this.start = start;
        this.deadline = deadline;
    }

    public int getSubtaskID()
    {
        return subtaskID;
    }

    public void setSubtaskID(int subtaskID)
    {
        this.subtaskID = subtaskID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getTaskId()
    {
        return taskId;
    }

    public void setTaskId(int taskId)
    {
        this.taskId = taskId;
    }

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public Date getStart()
    {
        return start;
    }

    public void setStart(Date start)
    {
        this.start = start;
    }

    public Date getDeadline()
    {
        return deadline;
    }

    public void setDeadline(Date deadline)
    {
        this.deadline = deadline;
    }

    @Override
    public String toString()
    {
        return "Subtask{" +
                "subtaskID=" + subtaskID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", userId=" + userId +
                ", start=" + start +
                ", deadline=" + deadline +
                '}';
    }
}
