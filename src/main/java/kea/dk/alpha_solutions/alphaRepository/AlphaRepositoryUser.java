package kea.dk.alpha_solutions.alphaRepository;

import kea.dk.alpha_solutions.model.User;
import org.mindrot.jbcrypt.BCrypt;
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
                boolean admin = resultSet.getBoolean(6);
                User user = new User (userId, mail, password, hourlyWage, name, admin);
                userList.add(user);
            }

        } catch (SQLException e) {
            System.out.println("Could not query database");
            e.printStackTrace();
        }

        return userList;

    }

    public User getUserByEmail(String email) {
        User user = new User();
        try (Connection connection = DriverManager.getConnection(DB_URL, UID, PWD)) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM user WHERE email=?");
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setUserId(resultSet.getInt("id"));
                user.setMail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setAdmin(resultSet.getBoolean("admin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println(user.getUserId());
        return user;
    }
    public void addUser(User user){
        try{
            //connect to db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);
            final String CREATE_QUERY = "INSERT INTO  alpha.user (id, email, password, hourlyWage, name, admin) VALUES  (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_QUERY);
            //Encrypt password
            String password = user.getPassword();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            //set attributer i prepared statement
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setString(2, user.getMail());
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setInt(4, user.getHourlyWage());
            preparedStatement.setString(5, user.getName());
            preparedStatement.setBoolean(6, user.isAdmin());


            //execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Could not create user");
            e.printStackTrace();
        }
    }
    public boolean isAdmin(String email) {
        boolean isAdmin = false;
        try (Connection connection = DriverManager.getConnection(DB_URL, UID, PWD)) {
            // Prepare the SQL query with a placeholder for the email parameter
            PreparedStatement statement = connection.prepareStatement("SELECT admin FROM user WHERE email=?");
            // Set the email parameter value in the query
            statement.setString(1, email);

            // Execute the query and retrieve the result set
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Retrieve the admin value from the result set
                int adminValue = resultSet.getInt("admin");

                // Check if the admin value is 1/true to determine the admin status
                isAdmin = (adminValue == 1);
            }
        } catch (SQLException e) {
            System.out.println("Could not determine admin status");
            e.printStackTrace();
        }
        return isAdmin;
    }
    public void deleteByEmail(String email){
        //SQL-query
        final String DELETE_QUERY = "DELETE FROM  alpha.user WHERE email=?";

        try {
            //connect til db
            Connection connection = DriverManager.getConnection(DB_URL, UID, PWD);

            //create statement
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);

            //set parameter
            preparedStatement.setString(1, email);

            //execute statement
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.out.println("Could not delete User");
            e.printStackTrace();
        }
    }
    public void updateUser(User user) {
        try (Connection connection = DriverManager.getConnection(DB_URL, UID, PWD)) {
            final String UPDATE_QUERY = "UPDATE alpha.user SET email=?, password=?, hourlyWage=?, name=?, admin=? WHERE id=?";
            int id = user.getUserId();
            String email = user.getMail();
            int hourlyWage = user.getHourlyWage();
            String name = user.getName();
            boolean admin = user.isAdmin();
            //Encrypt password
            String password = user.getPassword();
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            //Prepared statements
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setInt(3, hourlyWage);
            preparedStatement.setString(4, name);
            preparedStatement.setBoolean(5, admin);
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();
            System.out.println(user);
        } catch (SQLException e) {
            System.out.println("Could not update user");
            e.printStackTrace();
        }
    }
}

