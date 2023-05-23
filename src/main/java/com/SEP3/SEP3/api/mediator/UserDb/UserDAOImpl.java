package com.SEP3.SEP3.api.mediator.UserDb;

import ch.qos.logback.core.net.SMTPAppenderBase;
import com.SEP3.SEP3.api.mediator.DbConnection;
import com.SEP3.SEP3.api.model.DTOs.TutorInformationDto;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;

import java.sql.*;
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
    public User tutorByUsername(String username) {
        User user;
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM Users WHERE username = ? AND userType = ?");
            statement.setString(1, username);
            statement.setString(2, "Tutor");
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.getString("username") == null)
            {
                return null;
            }

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
    public TutorInformationDto getTutor(String userName) {
        TutorInformationDto tutor;
        try (Connection connection = DbConnection.getConnection()) {

            PreparedStatement getUserId = connection.prepareStatement("Select * from Users where username = ?");
            getUserId.setString(1, userName);
            ResultSet resultSet = getUserId.executeQuery();

            int userId = resultSet.getInt("id");

            User user = new User(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("userType"));
            user.setId(userId);

            PreparedStatement getDescription = connection.prepareStatement("Select * from Descriptions where user_id = ?");
            getDescription.setInt(1, userId);
            ResultSet resultSet2 = getDescription.executeQuery();

            String description = resultSet2.getString("description");

            PreparedStatement getCourses = connection.prepareStatement("Select name from Courses where id in (SELECT course_id from Tutors where user_id = ?)");
            getCourses.setInt(1, userId);
            ResultSet resultSet3 = getCourses.executeQuery();

            tutor = new TutorInformationDto(user, description);
            while (resultSet3.next())
            {
                tutor.addCourse(resultSet3.getString("name"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tutor;
    }

    @Override
    public User updateProfile(User user, String description, List<String> courses) {
        try (Connection connection = DbConnection.getConnection()) {
            // Delete existing courses for the specific user
            deleteExistingCourses(connection, user.getId());

            PreparedStatement updateDescription = connection.prepareStatement(
                    "UPDATE Descriptions SET description = ? where user_id = ?"
            );
            // Set the parameters in the prepared statement
            updateDescription.setString(1, description);
            updateDescription.setInt(2, user.getId());

            updateDescription.executeUpdate();

            // Prepare the SQL statement
            PreparedStatement updateCourse = connection.prepareStatement(
                    "INSERT INTO Tutors (user_id, course_id) VALUES (?, ?)");

            // Iterate over the list of courses
            for (String course : courses) {
                // Find the course ID for the given course name
                int courseId = findCourseId(connection, course);

                // Set the parameters in the prepared statement
                updateCourse.setInt(1, user.getId());
                updateCourse.setInt(2, courseId);

                // Execute the insert statement
                updateCourse.executeUpdate();
            }
            // Close the prepared statement
            updateCourse.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean deleteAccount(int userId) {
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("DELETE FROM Users WHERE id = ?");
            statement2.setInt(1, userId);

            statement2.executeUpdate();
            connection.close();

            return true;

        } catch (SQLException throwable) {

            return false;
        }
    }

    // Helper method to delete existing courses for a specific user
        private void deleteExistingCourses(Connection connection, int userId) throws SQLException {
            try (PreparedStatement deleteCourses = connection.prepareStatement(
                    "DELETE FROM Tutors WHERE user_id = ?")) {
                deleteCourses.setInt(1, userId);
                deleteCourses.executeUpdate();
            }
        }

        // Helper method to find the course ID for a given course name
        private int findCourseId(Connection connection, String courseName) throws SQLException {
            int courseId = -1;  // Default value if the course is not found

            try (PreparedStatement findCourse = connection.prepareStatement(
                    "SELECT id FROM Courses WHERE name = ?")) {
                findCourse.setString(1, courseName);

                try (ResultSet resultSet = findCourse.executeQuery()) {
                    if (resultSet.next()) {
                        courseId = resultSet.getInt("id");
                    }
                }
            }

            return courseId;
        }
    }

