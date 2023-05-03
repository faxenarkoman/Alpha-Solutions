package kea.dk.alpha_solutions.model;

import java.util.ArrayList;
import java.util.Date;

public class Project {
    private int projectID;
    private String title;
    private String description;
    private Date deadline;
    private int nrOfUsers;
    private int nrOfHours;
    private ArrayList<Task> tasks = new ArrayList<>();
    private double projectPrice;
    private int hoursPerDay;
}
