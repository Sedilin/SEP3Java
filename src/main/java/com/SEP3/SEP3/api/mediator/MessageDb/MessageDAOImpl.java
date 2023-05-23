package com.SEP3.SEP3.api.mediator.MessageDb;

import com.SEP3.SEP3.api.mediator.DbConnection;
import com.SEP3.SEP3.api.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MessageDAOImpl implements MessageDAO {
    @Override
    public boolean archiveMessage(User sender, String message, User receiver) {

        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO Messages(sender_id, recipient_id, message) VALUES (?,?, ?)");
            statement.setInt(1, sender.getId());
            statement.setInt(2, receiver.getId());
            statement.setString(3, message);


            statement.executeUpdate();
            connection.close();

            return true;
        } catch (SQLException throwable) {

            throw new RuntimeException(throwable);
        }
    }
}











