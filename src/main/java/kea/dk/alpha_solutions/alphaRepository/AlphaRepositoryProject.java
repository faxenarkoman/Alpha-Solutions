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
                        final String SQL_QUERY = "SELECT * FROM alphas.project";
                        ResultSet resultSet = statement.executeQuery(SQL_QUERY);
                        while (resultSet.next()) {
                                int userID = resultSet.getInt(1);
                                int projectID = resultSet.getInt(2);
                                String projectTitle = resultSet.getString(3);
                                String description = resultSet.getString(4);
                                String deadline = resultSet.getString(5);
                                int nrOfUsers = resultSet.getInt(6);
                                int nrOfHours = resultSet.getInt(7);
                                double projectPrice = resultSet.getDouble(8);
                                int hoursPerDay = resultSet.getInt(9);
                                Project project = new Project(userID, projectID, projectTitle, description,
                                deadline, nrOfUsers, nrOfHours, projectPrice, hoursPerDay);
                                projectList.add(project);
                                System.out.println(project);
                        }

                } catch (SQLException e) {
                        System.out.println("Could not query database");
                        e.printStackTrace();
                }

                return projectList;

        }

        public void addProject(Project project){
                if (project.getProjectTitle() == null) {
                        throw new IllegalArgumentException("Project object must have a non-null title attribute");
                }
                try{
                        //connect to db
                        Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
                        final String CREATE_QUERY = "INSERT INTO  aplahs.project (userID, projectID, projectTitle, description, deadline, nrOfUsers, nrOfHours, projectPrice, hoursPerDay) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

                        //set attributer i prepared statement
                        preparedStatement.setInt(1, project.getUserID());
                        preparedStatement.setInt(2, project.getProjectID());
                        preparedStatement.setString(3, project.getProjectTitle());
                        preparedStatement.setString(4, project.getProjectDescription());
                        preparedStatement.setString(5, project.getDeadline());
                        preparedStatement.setInt(6, project.getNrOfHours());
                        preparedStatement.setInt(7, project.getNrOfUsers());
                        preparedStatement.setDouble(8, project.getProjectPrice());
                        preparedStatement.setInt(9, project.getHoursPerDay());

                        //execute statement
                        preparedStatement.executeUpdate();
                } catch (SQLException e) {
                        System.out.println("Could not create product");
                        e.printStackTrace();
                }
        }

        public void updateProduct(Project project)
        {
                //SQL statement
                final String UPDATE_QUERY = "UPDATE  wishlisters.wish SET userID = ?, projectTitle = ?, projectDescription = ?, deadline = ?, nrOfUsers = ?, nrOfHours = ?, projectPrice = ?, HoursPrDay = ?  WHERE projectID = ?,";

                try {
                        //connect db
                        Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

                        //prepared statement
                        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);

                        //set parameters
                        int userID = project.getUserID();
                        int projectID = project.getProjectID();
                        String projectTitle = project.getProjectTitle();
                        String projectDescription = project.getProjectDescription();
                        String deadline = project.getDeadline();
                        int nrOfUsers = project.getNrOfUsers();
                        int nrOfHours = project.getNrOfHours();
                        int hoursPrDay = project.getHoursPerDay();

                        preparedStatement.setInt(1, userID);
                        preparedStatement.setDouble(2, projectID);
                        preparedStatement.setString(3, projectTitle);
                        preparedStatement.setString(4, projectDescription);
                        preparedStatement.setString(5, deadline);
                        preparedStatement.setInt(6, nrOfUsers);
                        preparedStatement.setInt(7, nrOfHours);
                        preparedStatement.setInt(8, hoursPrDay);

                        //execute statement
                        preparedStatement.executeUpdate();

                } catch (SQLException e) {
                        System.out.println("Could not update product");
                        e.printStackTrace();
                }
        }
}



