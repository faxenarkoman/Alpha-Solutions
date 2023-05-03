package kea.dk.alpha_solutions.model;

import java.util.ArrayList;

public class Task {
    private int taskId;
    private int projectId;
    private String name;
    private String description;
    private String startDate;
    private String deadline;
    private int hours;
    private ArrayList<Subtask> subtasks = new ArrayList<Subtask>();

}
