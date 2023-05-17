package kea.dk.alpha_solutions.alphaRepository;

import kea.dk.alpha_solutions.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AlphaRepositoryUser {
    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String UID;
    @Value("${spring.datasource.password}")
    private String PWD;

    public List<User> getAll()
    {
        List<User> userList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            Statement statement = connection.createStatement();
            final String SQL_QUERY = "SELECT * FROM alpha.user";
            ResultSet resultSet = statement.executeQuery(SQL_QUERY);
            while (resultSet.next()) {
                int userId = resultSet.getInt(1);
                String mail = resultSet.getString(2);
                String password = resultSet.getString(3);
                int hourlyWage = resultSet.getInt(4);
                String name = resultSet.getString(5);
                User user = new User (userId, mail, password, hourlyWage, name);
                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Could not query database");
            e.printStackTrace();
        }

        return userList;

    }

    public User getUserByEmail(String email) {
        User user = null;
        try (Connection connection = DriverManager.getConnection(DB_URL, UID, PWD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email=?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String userId = resultSet.getString("id");
                String passwordHash = resultSet.getString("password");
                user = new User(userId, email, passwordHash);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    public void addUser(User user){
        if (getUserByEmail(user.getMail()) == null) {
            throw new IllegalArgumentException("User object must have a non-null name attribute");
        }
        try{
            //connect to db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            final String CREATE_QUERY = "INSERT INTO  alpha.user (id, email, password, hourlyWage, name) VALUES  (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);

            //set attributer i prepared statement
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(3, user.getMail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(4, user.getHourlyWage());
            preparedStatement.setString(5, user.getName());

            //execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not create user");
            e.printStackTrace();
        }
    }
}

