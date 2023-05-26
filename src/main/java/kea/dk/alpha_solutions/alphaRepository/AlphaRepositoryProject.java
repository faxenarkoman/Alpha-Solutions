package kea.dk.alpha_solutions.alphaRepository;

import kea.dk.alpha_solutions.model.Project;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlphaRepositoryProject
{

        @Value("${spring.datasource.url}")
        private String DB_URL;
        @Value("${spring.datasource.username}")
        private String UID;
        @Value("${spring.datasource.password}")
        private String PWD;




        public List<Project> getAll()
        {
                List<Project> projectList = new ArrayList<>();
                try {
                        Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
                        Statement statement = connection.createStatement();
                        final String SQL_QUERY = "SELECT * FROM alpha.project";
                        ResultSet resultSet = statement.executeQuery(SQL_QUERY);
                        while (resultSet.next()) {
                                int projectId = resultSet.getInt(1);
                                String projectTitle = resultSet.getString(2);
                                String deadline = resultSet.getString(3);
                                int hoursPerDay = resultSet.getInt(4);
                                String projectDescription = resultSet.getString(5);
                                Project project = new Project(projectId, projectTitle, deadline,
                                projectDescription, hoursPerDay);
                                projectList.add(project);
                        }

                } catch (SQLException e) {
                        System.out.println("Could not query database");
                        e.printStackTrace();
                }

                return projectList;

        }

        public void addProject(Project project) {
                if (project.getProjectTitle() == null) {
                        throw new IllegalArgumentException("Project object must have a non-null title attribute");
                }
                try {
                        // Connect to the database
                        Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
                        final String CREATE_QUERY = "INSERT INTO alpha.project (projectID, projectTitle, deadline, hoursPrDay, projectDescription) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

                        // Set attributes in the prepared statement
                        preparedStatement.setInt(1, project.getProjectID());
                        preparedStatement.setString(2, project.getProjectTitle());
                        preparedStatement.setString(3, project.getDeadline());
                        preparedStatement.setInt(4, project.getHoursPerDay());
                        preparedStatement.setString(5, project.getProjectDescription());


                        // Execute the statement
                        preparedStatement.executeUpdate();
                } catch (SQLException e) {
                        System.out.println("Could not create project");
                        e.printStackTrace();
                }
        }



        public void updateProject(Project project)
        {
                //SQL statement
                final String UPDATE_QUERY = "UPDATE  alpha.project SET projectTitle = ?, projectDescription = ?, deadline = ?, HoursPrDay = ?  WHERE projectID = ?";

                try {
                        //connect db
                        Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

                        //prepared statement
                        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

                        //set parameters
                        int projectID = project.getProjectID();
                        String projectTitle = project.getProjectTitle();
                        String projectDescription = project.getProjectDescription();
                        String deadline = project.getDeadline();
                        int hoursPrDay = project.getHoursPerDay();

                        preparedStatement.setDouble(1, projectID);
                        preparedStatement.setString(2, projectTitle);
                        preparedStatement.setString(3, projectDescription);
                        preparedStatement.setString(4, deadline);
                        preparedStatement.setInt(5, hoursPrDay);

                        //execute statement
                        preparedStatement.executeUpdate();

                } catch (SQLException e) {
                        System.out.println("Could not update product");
                        e.printStackTrace();
                }
        }

        public void deleteByProjectID(int projectID){
                //SQL-query
                final String DELETE_QUERY = "DELETE FROM  alpha.project WHERE projectID=?";

                try {
                        //connect til db
                        Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

                        //create statement
                        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);

                        //set parameter
                        preparedStatement.setInt(1, projectID);

                        //execute statement
                        preparedStatement.executeUpdate();

                } catch (SQLException e){
                        System.out.println("Could not delete product");
                        e.printStackTrace();
                }
        }

        public Project getProjectByID(int projectID){
                //SQL-statement
                final String FIND_QUERY = "SELECT * FROM  alpha.project WHERE projectID = ?";
                Project project =  new Project();
                project.setProjectID(projectID);
                try {
                        //db connection
                        Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

                        //prepared statement
                        PreparedStatement preparedStatement = connection.prepareStatement(FIND_QUERY);

                        //set parameters
                        preparedStatement.setInt(1, projectID);

                        //execute statement
                        ResultSet resultSet = preparedStatement.executeQuery();

                        //få product ud af resultset
                        resultSet.next();
                        String projectTitle = resultSet.getString(2);
                        String deadline = resultSet.getString(3);
                        int nrOfHours = resultSet.getInt(4);
                        int nrOfUsers = resultSet.getInt(5);
                        double projectPrice = resultSet.getDouble(6);
                        int hoursPerDay = resultSet.getInt(7);
                        String projectDescription = resultSet.getString(8);

                        project.setProjectID(projectID);
                        project.setProjectTitle(projectTitle);
                        project.setProjectDescription(projectDescription);
                        project.setDeadline(deadline);
                        project.setHoursPerDay(hoursPerDay);

                } catch (SQLException e){
                        System.out.println("Could not find project");
                        e.printStackTrace();
                }
                
                //return project
                return project;
        }
}



