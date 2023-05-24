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






    public List<Task> getAllTasks()
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



    public void updateTask(Task task)
    {
        //SQL statement
        final String UPDATE_QUERY = "UPDATE  alpha.task SET taskName = ?, taskNrOfHours = ?, tasknrOfUsers = ?, taskDesciption = ?, taskDeadline = ?, taskHoursPrDay = ? WHERE taskID = ?";

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

            preparedStatement.setInt(1,taskId);
            preparedStatement.setString(2, taskName);
            preparedStatement.setInt(3, taskNrOfHours);
            preparedStatement.setInt(4, taskNrOfUsers);
            preparedStatement.setString(5, taskDeadline);
            preparedStatement.setInt(6, taskHoursPrDay);

            //execute statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Could not update product");
            e.printStackTrace();
        }
    }

    public void deleteByProjectID(int taskId){
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
            System.out.println("Could not delete product");
            e.printStackTrace();
        }
    }

    public Task getTaskByID(int taskId){
        //SQL-statement
        final String FIND_QUERY = "SELECT * FROM  alpha.task WHERE taskId = ?";
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
            String taskName = resultSet.getString(2);
            int taskNrOfHours = resultSet.getInt(3);
            int taskNrOfUsers = resultSet.getInt(4);
            String taskDescription = resultSet.getString(5);
            String taskDeadline = resultSet.getString(6);
            int taskHoursPrDay = resultSet.getInt(7);

            task.setTaskId(taskId);
            task.setTaskName(taskName);
            task.setTaskNrOfHours(taskNrOfHours);
            task.setTaskNrOfUsers(taskNrOfUsers);
            task.setTaskDescription(taskDescription);
            task.setTaskDeadline(taskDeadline);
            task.setTaskHoursPrDay(taskHoursPrDay);


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
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO task (task_name, project_id) VALUES (?, ?)");
            preparedStatement.setString(1, task.getTaskName());
            preparedStatement.setInt(2, project.getProjectID());
            preparedStatement.executeUpdate();

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
        final String FIND_QUERY = "SELECT * FROM task WHERE project_id = ?";
        List<Task> tasks = new ArrayList<>();

        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);
            preparedStatement.setInt(1, projectID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Task task = new Task();
                task.setTaskId(resultSet.getInt("task_id"));
                task.setTaskName(resultSet.getString("task_name"));
                task.setTaskNrOfHours(resultSet.getInt("task_nr_of_hours"));
                task.setTaskNrOfUsers(resultSet.getInt("task_nr_of_users"));
                task.setTaskDescription(resultSet.getString("task_description"));
                task.setTaskDeadline(resultSet.getString("task_deadline"));
                task.setTaskHoursPrDay(resultSet.getInt("task_hours_per_day"));

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
