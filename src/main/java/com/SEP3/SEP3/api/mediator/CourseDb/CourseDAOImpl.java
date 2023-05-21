package com.SEP3.SEP3.api.mediator.CourseDb;

import com.SEP3.SEP3.api.mediator.CourseDb.CourseDAO;
import com.SEP3.SEP3.api.mediator.DbConnection;
import com.SEP3.SEP3.api.model.DTOs.TutorInformationDto;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOImpl implements CourseDAO {
    private User user;

    @Override
    public List<UserToTutorDto> tutorByCourse(String courseName) {
        List<UserToTutorDto> tutors = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection()) {
            // Find the course ID by name
            PreparedStatement courseStatement = connection.prepareStatement(
                    "SELECT id FROM Courses WHERE name = ?"
            );
            courseStatement.setString(1, courseName);
            ResultSet courseResultSet = courseStatement.executeQuery();

            if (courseResultSet.next()) {
                int courseId = courseResultSet.getInt("id");

                // Find the tutors teaching the course
                PreparedStatement tutorStatement = connection.prepareStatement(
                        "SELECT U.id AS userId, U.username, U.password, C.name AS courseName, D.description " +
                                "FROM Users U " +
                                "INNER JOIN Tutors T ON U.id = T.user_id " +
                                "INNER JOIN Courses C ON T.course_id = C.id " +
                                "INNER JOIN Descriptions D ON U.id = D.user_id " +
                                "WHERE C.id = ? AND U.userType = 'Tutor'"
                );
                tutorStatement.setInt(1, courseId);
                ResultSet tutorResultSet = tutorStatement.executeQuery();

                while (tutorResultSet.next()) {
                    int userId = tutorResultSet.getInt("userId");
                    String username = tutorResultSet.getString("username");
                    String password = tutorResultSet.getString("password");
                    String tutorCourseName = tutorResultSet.getString("courseName");
                    String tutorDescription = tutorResultSet.getString("description");

                    User user = new User(username, password);
                    user.setId(userId); // Set the correct user ID

                    UserToTutorDto tutorDto = new UserToTutorDto(
                            user,
                            tutorCourseName,
                            tutorDescription
                    );

                    tutors.add(tutorDto);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return tutors;
    }

    @Override
    public List<String> getAllCourses() {
        List<String> courses = new ArrayList<>();

        try (Connection connection = DbConnection.getConnection()) {
            // Find the course ID by name
            PreparedStatement courseStatement = connection.prepareStatement("SELECT name FROM Courses");
            ResultSet result = courseStatement.executeQuery();

            while (result.next()) {
                courses.add(result.getString("name"));
            }

            return courses;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserToTutorDto deleteCourse(User user, String course, String description) {
        try (Connection connection = DbConnection.getConnection()) {

            // Check if the user has only one assigned course
            PreparedStatement checkCourseCount = connection.prepareStatement(
                    "SELECT COUNT(*) AS courseCount FROM Tutors WHERE user_id = ?"
            );
            checkCourseCount.setInt(1, user.getId());
            ResultSet countResultSet = checkCourseCount.executeQuery();
            countResultSet.next();
            int courseCount = countResultSet.getInt("courseCount");

            if (courseCount == 1) {
                // Delete the user's course and description
                PreparedStatement deleteTutorCourse = connection.prepareStatement(
                        "DELETE FROM Tutors WHERE user_id = ?"
                );
                deleteTutorCourse.setInt(1, user.getId());
                deleteTutorCourse.executeUpdate();

                PreparedStatement deleteDescription = connection.prepareStatement(
                        "DELETE FROM Descriptions WHERE user_id = ?"
                );
                deleteDescription.setInt(1, user.getId());
                deleteDescription.executeUpdate();

                // Change the user's userType to "User"
                PreparedStatement changeUserType = connection.prepareStatement(
                        "UPDATE Users SET userType = 'User' WHERE id = ?"
                );
                changeUserType.setInt(1, user.getId());
                changeUserType.executeUpdate();

                return new UserToTutorDto(user, null, null);
            } else {
                // Delete only the specific course
                PreparedStatement deleteTutorCourse = connection.prepareStatement(
                        "DELETE FROM Tutors WHERE user_id = ? AND course_id = ?"
                );
                deleteTutorCourse.setInt(1, user.getId());
                deleteTutorCourse.setInt(2, getCourseIdByName(course));
                deleteTutorCourse.executeUpdate();

                return new UserToTutorDto(user, course, description);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int getCourseIdByName(String courseName) // needed for delete method
    {
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT id FROM Courses WHERE name = ?"
            );
            statement.setString(1, courseName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalArgumentException("Course not found");
    }


}
