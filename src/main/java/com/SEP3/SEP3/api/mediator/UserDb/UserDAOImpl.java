package com.SEP3.SEP3.api.mediator.UserDb;

import com.SEP3.SEP3.api.mediator.DbConnection;
import com.SEP3.SEP3.api.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO{
    @Override
    public boolean addUser(User user) {
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO Users(username,\"password\") VALUES (?,?);");
            statement2.setString(1, user.getUserName());
            statement2.setString(2, user.getPassword());

            if(!isUserNameExistent(user.getUserName()))
            {
                statement2.executeUpdate();
                connection.close();
                return true;
            }

        } catch (SQLException throwable) {

            throw new RuntimeException(throwable);
        }
        return false;
    }


    public boolean isUserNameExistent(String username){
        try(Connection connection = DbConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement("SELECT * from Users where username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
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
        try(Connection connection = DbConnection.getConnection()){
            PreparedStatement statement = connection.prepareStatement("Select * from Users");
            ResultSet resultSet = statement.executeQuery();

            System.out.println(resultSet + "printed resultset");

            String username;
            String password;


            while (resultSet.next()){

                username = resultSet.getString("username");
                password = resultSet.getString("password");
                a.add(new User(username,password));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return a;
    }
}
