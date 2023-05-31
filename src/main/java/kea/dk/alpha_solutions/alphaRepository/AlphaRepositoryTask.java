package kea.dk.alpha_solutions.alphaRepository;

import kea.dk.alpha_solutions.model.Project;
import kea.dk.alpha_solutions.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlphaRepositoryTask
{

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String UID;
    @Value("${spring.datasource.password}")
    private String PWD;

    public void updateTask(int taskID, Task task)
    {
        //SQL statement
        final String UPDATE_QUERY = "UPDATE  alpha.task SET taskName = ?, taskNrOfHours = ?, tasknrOfUsers = ?, taskDescription = ?, taskDeadline = ?, taskHoursPrDay = ?, completed = ? WHERE taskID = ?";

        try {
            //connect db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

            //set parameters
            int taskId = task.getTaskId();
            String taskName = task.getTaskName();
            int taskNrOfHours = task.getTaskNrOfHours();
            int taskNrOfUsers = task.getTaskNrOfUsers();
            String taskDeadline = task.getTaskDeadline();
            int taskHoursPrDay = task.getTaskHoursPrDay();
            String taskDescription = task.getTaskDescription();
            boolean completed = task.getCompleted();

            System.out.println(completed);

            preparedStatement.setInt(8,taskId);
            preparedStatement.setString(1, taskName);
            preparedStatement.setInt(2, taskNrOfHours);
            preparedStatement.setInt(3, taskNrOfUsers);
            preparedStatement.setString(5, taskDeadline);
            preparedStatement.setInt(6, taskHoursPrDay);
            preparedStatement.setString(4, taskDescription);
            preparedStatement.setBoolean(7, completed);


            //execute statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not update task");
            e.printStackTrace();
        }
    }

    public void deleteByTaskID(int taskId){
        //SQL-query
        final String DELETE_QUERY = "DELETE FROM  alpha.task WHERE taskId=?";

        try {
            //connect til db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

            //create statement
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);

            //set parameter
            preparedStatement.setInt(1, taskId);

            //execute statement
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Could not delete task");
            e.printStackTrace();
        }
    }

    public Task getTaskByID(int taskId){
        //SQL-statement
        final String FIND_QUERY = "SELECT * FROM  alpha.task WHERE taskID = ?";
        Task task = new Task();
        task.setTaskId(taskId);
        try {
            //db connection
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

            //prepared statement
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);

            //set parameters
            preparedStatement.setInt(1, taskId);

            //execute statement
            ResultSet resultSet = preparedStatement.executeQuery();




            //få product ud af resultset
            resultSet.next();
            String taskName = resultSet.getString(3);
            int taskNrOfHours = resultSet.getInt(4);
            int taskNrOfUsers = resultSet.getInt(5);
            String taskDescription = resultSet.getString(2);
            String taskDeadline = resultSet.getString(7);
            int taskHoursPrDay = resultSet.getInt(6);
            int projectId = resultSet.getInt(8);
            boolean completed = resultSet.getBoolean(9);

            task.setTaskId(taskId);
            task.setTaskName(taskName);
            task.setTaskNrOfHours(taskNrOfHours);
            task.setTaskNrOfUsers(taskNrOfUsers);
            task.setTaskDescription(taskDescription);
            task.setTaskDeadline(taskDeadline);
            task.setTaskHoursPrDay(taskHoursPrDay);
            task.setProjectId(projectId);
            task.setCompleted(completed);


        } catch (SQLException e){
            System.out.println("Could not find task");
            e.printStackTrace();
        }
        System.out.println(task);
        //return task
        return task;
    }

    public Task createTaskInProject(Project project, Task task) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO task (taskName, projectId, taskNrOfHours, taskNrOfUsers, taskDescription, taskDeadline, taskHoursPrDay, Completed) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, project.getProjectID());
            preparedStatement.setInt(3, task.getTaskNrOfHours());
            preparedStatement.setInt(4, task.getTaskNrOfUsers());
            preparedStatement.setString(5, task.getTaskDescription());
            preparedStatement.setString(6, task.getTaskDeadline());
            preparedStatement.setInt(7, task.getTaskHoursPrDay());
            preparedStatement.setBoolean(8, task.getCompleted());
            preparedStatement.executeUpdate();

            // Retrieve the generated task ID
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int taskId = generatedKeys.getInt(1);
                task.setTaskId(taskId);
            }

            preparedStatement.close();
            connection.close();
            System.out.println("Task created successfully.");
        } catch (SQLException e) {
            System.out.println("Failed to create task.");
            e.printStackTrace();
        }
        return task;
    }



    public List<Task> getTasksByProjectID(int projectID) {
        final String FIND_QUERY = "SELECT * FROM task WHERE projectId = ?";
        List<Task> tasks = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);
            preparedStatement.setInt(1, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("taskId"));
                task.setTaskName(resultSet.getString("taskName"));
                task.setTaskNrOfHours(resultSet.getInt("taskNrOfHours"));
                task.setTaskNrOfUsers(resultSet.getInt("taskNrOfUsers"));
                task.setTaskDescription(resultSet.getString("taskDescription"));
                task.setTaskDeadline(resultSet.getString("taskDeadline"));
                task.setTaskHoursPrDay(resultSet.getInt("taskHoursPrDay"));
                task.setCompleted(resultSet.getBoolean("Completed"));

                tasks.add(task);
            }

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Could not find tasks");
            e.printStackTrace();
        }

        return tasks;
    }

}
