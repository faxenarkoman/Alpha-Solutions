package kea.dk.alpha_solutions.alphaRepository;

import kea.dk.alpha_solutions.model.Project;
import kea.dk.alpha_solutions.model.Task;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlphaRepositoryTask
{

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String UID;
    @Value("${spring.datasource.password}")
    private String PWD;





    public List<Task> getAll()
    {
        List<Task> taskList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM alphas.task";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                int taskId = resultSet.getInt(1);
                String taskName = resultSet.getString(2);
                int taskNrOfHours = resultSet.getInt(3);
                int taskNrOfUsers = resultSet.getInt(4);
                String taskDescription = resultSet.getString(5);
                String taskDeadline = resultSet.getString(6);
                int taskHoursPrDay = resultSet.getInt(7);

                Task task = new Task(taskId, taskName, taskNrOfHours, taskNrOfUsers, taskDescription, taskDeadline, taskHoursPrDay);
                taskList.add(task);
                System.out.println(task);
            }

        } catch (SQLException e) {
            System.out.println("Could not query database");
            e.printStackTrace();
        }

        return taskList;
    }
    private int taskId;
    private String taskName;
    private int taskNrOfHours;
    private int taskNrOfUsers;
    private String taskDescription;

    private String taskDeadline;
    private int taskHoursPrDay;

    public void addTask(Task task){
        if (task.getTaskName() == null) {
            throw new IllegalArgumentException("Project object must have a non-null title attribute");
        }
        try{
            //connect to db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            final String CREATE_QUERY = "INSERT INTO  aplahs.task (taskId, taskName, taskDescription, taskNrOfUsers, taskNrOfHours, taskDescription, taskDeadline, taskHoursPrDay) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            //set attributer i prepared statement
            preparedStatement.setInt(1, task.getTaskId());


            //execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not create product");
            e.printStackTrace();
        }
    }



}
