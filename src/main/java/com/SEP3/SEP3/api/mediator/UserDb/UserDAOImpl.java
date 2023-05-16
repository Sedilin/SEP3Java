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

    @Override
    public User becomeTutor(User user, String course, String description) {
        try (Connection connection = DbConnection.getConnection()) {

            //Adds description
            PreparedStatement addDescription = connection.prepareStatement("INSERT INTO Descriptions(user_id, description) VALUES (?, ?)");
            addDescription.setInt(1, user.getId());
            addDescription.setString(2, description);
            addDescription.executeUpdate();

            //Search for course id
            int course_id = 0;

            PreparedStatement getCourse = connection.prepareStatement("Select * from Courses where name = ?");
            getCourse.setString(1, course);

            ResultSet resultSet = getCourse.executeQuery();

            course_id = resultSet.getInt("id");

            //Add course id to a user
            PreparedStatement addTutor = connection.prepareStatement("INSERT INTO Tutors(user_id, course_id) VALUES (?, ?)");
            addTutor.setInt(1, user.getId());
            addTutor.setInt(2, course_id);

            addTutor.executeUpdate();

            //Change type of user
            PreparedStatement changeToTutor = connection.prepareStatement("Update Users set userType='Tutor' where id = ?" );
            changeToTutor.setInt(1, user.getId());

            changeToTutor.executeUpdate();

            connection.close();

            return getByUsername(user.getUserName());
        } catch (SQLException throwable) {

            throw new RuntimeException(throwable);
        }
    }

    @Override
    public String getDescription(String userName) {
        String description = "";
        try (Connection connection = DbConnection.getConnection()) {

            PreparedStatement getUserId = connection.prepareStatement("Select * from Users where username = ?");
            getUserId.setString(1, userName);
            ResultSet resultSet = getUserId.executeQuery();

            int userId = resultSet.getInt("id");

            PreparedStatement statement = connection.prepareStatement("Select * from Descriptions where user_id = ?");
            statement.setInt(1, userId);
            ResultSet resultSet2 = statement.executeQuery();

            description = resultSet2.getString("description");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return description;
    }
}
