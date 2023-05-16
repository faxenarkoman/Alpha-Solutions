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
                int projectId = resultSet.getInt(2);
                String name = resultSet.getString(3);
                String description = resultSet.getString(4);
                String startDate = resultSet.getString(5);
                String deadline = resultSet.getString( 6);
                int hours = resultSet.getInt(7);
                Task task = new Task(taskId, projectId, name, description, startDate, deadline, hours);
                taskList.add(task);
                System.out.println(task);
            }

        } catch (SQLException e) {
            System.out.println("Could not query database");
            e.printStackTrace();
        }

        return taskList;
    }

    public void addTask(Task task){
        if (task.getName() == null) {
            throw new IllegalArgumentException("Project object must have a non-null title attribute");
        }
        try{
            //connect to db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            final String CREATE_QUERY = "INSERT INTO  aplahs.task (taskId, projectId, name, taskDescription, startDate, deadline, hours) VALUES  (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            //set attributer i prepared statement
            preparedStatement.setInt(1, task.getTaskId());
            preparedStatement.setInt(2, task.getProjectId());
            preparedStatement.setString(3, task.getName());
            preparedStatement.setString(4, task.getTaskDescription());
            preparedStatement.setString(5, task.getStartDate());
            preparedStatement.setString(6, task.getDeadline());
            preparedStatement.setDouble(7, task.getHours());

            //execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not create product");
            e.printStackTrace();
        }
    }



}
