package kea.dk.alpha_solutions.alphaRepository;

import kea.dk.alpha_solutions.model.Subtask;
import kea.dk.alpha_solutions.model.Task;
import org.springframework.beans.factory.annotation.Value;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlphaRepositorySubTask
{

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String UID;
    @Value("${spring.datasource.password}")
    private String PWD;

    public List<Subtask> getAll()
    {
        List<Subtask> subtaskList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM alphas.subtask";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                int subtaskID = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                int taskId = resultSet.getInt(4);
                int userId = resultSet.getInt(5);
                String start = resultSet.getString(5);
                String deadline = resultSet.getString( 6);
                int hours = resultSet.getInt(7);
                Subtask subtask = new Subtask(subtaskID, name, description, taskId, userId, start,
                deadline);
                subtaskList.add(subtask);
                System.out.println(subtask);
            }

        } catch (SQLException e) {
            System.out.println("Could not query database");
            e.printStackTrace();
        }

        return subtaskList;
    }

}
