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
                String userId = resultSet.getString(1);
                String mail = resultSet.getString(2);
                String password = resultSet.getString(3);
                User user = new User (userId, mail, password);
                userList.add(user);
                System.out.println(mail);
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
}

