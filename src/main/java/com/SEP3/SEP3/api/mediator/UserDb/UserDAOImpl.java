package com.SEP3.SEP3.api.mediator.UserDb;

import com.SEP3.SEP3.api.mediator.DbConnection;
import com.SEP3.SEP3.api.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    @Override
    public User addUser(User user) {

        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO Users(username,\"password\", userType) VALUES (?,?, ?)");
            statement2.setString(1, user.getUserName());
            statement2.setString(2, user.getPassword());
            statement2.setString(3, user.getUserType());

            statement2.executeUpdate();
            connection.close();

            return getByUsername(user.getUserName());
        } catch (SQLException throwable) {

            throw new RuntimeException(throwable);
        }
    }


    public boolean isUserNameExistent(String username) {
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * from Users where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return false;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> a = new ArrayList<>();
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("Select * from Users");
            ResultSet resultSet = statement.executeQuery();


            int userId;
            String userType;
            String username;
            String password;


            while (resultSet.next()) {
                userId = resultSet.getInt("id");
                userType = resultSet.getString("userType");
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                User user = new User(username, password, userType);
                user.setId(userId);
                a.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return a;
    }

    @Override
    public User getByUsername(String username) {
        User user;
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("Select * from Users where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            user = new User(resultSet.getString("username"),
                    resultSet.getString("password"));

            user.setId(resultSet.getInt("id"));
            user.setUserType(resultSet.getString("userType"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
