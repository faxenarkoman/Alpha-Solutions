package kea.dk.alpha_solutions.alphaRepository;

import kea.dk.alpha_solutions.alphaController.AlphaController;
import kea.dk.alpha_solutions.model.Project;
import kea.dk.alpha_solutions.model.Task;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AlphaRepository
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
                                int projectID = resultSet.getInt(1);
                                String title = resultSet.getString(2);
                                String description = resultSet.getString(3);
                                String deadline = resultSet.getString(4);
                                int nrOfUsers = resultSet.getInt(5);
                                int nrOfHours = resultSet.getInt(6);
                                String tasks = resultSet.getString(7);
                                int projectPrice = resultSet.getInt(8);
                                int hoursPerDay = resultSet.getInt(9);
                                Project project = new Project(projectID, title, description,
                                deadline, nrOfUsers, nrOfHours, tasks, projectPrice, hoursPerDay);
                                projectList.add(project);
                                System.out.println(project);
                        }

                } catch (SQLException e) {
                        System.out.println("Could not query database");
                        e.printStackTrace();
                }

                return WishList;

        }
}



