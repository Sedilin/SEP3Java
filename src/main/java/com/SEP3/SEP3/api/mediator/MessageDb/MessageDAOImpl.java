package com.SEP3.SEP3.api.mediator.MessageDb;

import com.SEP3.SEP3.api.mediator.DbConnection;
import com.SEP3.SEP3.api.model.DTOs.MessageDto;
import com.SEP3.SEP3.api.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<MessageDto> showMessages(int loggedUserId, int otherUserId) {

        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT M.message_id,\n" +
                    "       M.message,\n" +
                    "       M.sender_id,\n" +
                    "       M.recipient_id,\n" +
                    "       sender.username as senderUserName,\n" +
                    "       sender.password as senderPassword,\n" +
                    "       sender.userType as senderUserType,\n" +
                    "       recipient.username as recipientUserName,\n" +
                    "       recipient.password as recipientPassword,\n" +
                    "       recipient.userType as recipientUserType\n" +
                    "FROM Messages M\n" +
                    "         INNER JOIN Users sender ON M.sender_id = sender.id\n" +
                    "         INNER JOIN Users recipient ON recipient_id = recipient.id\n" +
                    "WHERE (M.sender_id = ? AND M.recipient_id = ?)\n" +
                    "   OR (M.sender_id = ? AND M.recipient_id = ?)");

            statement.setInt(1, loggedUserId);
            statement.setInt(2, otherUserId);
            statement.setInt(3, otherUserId);
            statement.setInt(4, loggedUserId);


            ResultSet resultSet = statement.executeQuery();

            List<MessageDto> dtos = new ArrayList<>();

            while (resultSet.next()) {

                String Message = resultSet.getString("message");

                int sender_id = resultSet.getInt("sender_id");
                String sender_username = resultSet.getString("senderUserName");
                String sender_password = resultSet.getString("senderPassword");
                String sender_userType = resultSet.getString("senderUserType");

                User Sender = new User(sender_id, sender_username, sender_password, sender_userType);

                int recipient_id = resultSet.getInt("recipient_id");
                String recipient_username = resultSet.getString("recipientUserName");
                String recipient_password = resultSet.getString("recipientPassword");
                String recipient_userType = resultSet.getString("recipientUserType");

                User Recipient = new User(recipient_id, recipient_username, recipient_password, recipient_userType);

                dtos.add(new MessageDto(Sender, Recipient, Message));
            }

            return dtos;

        } catch (SQLException throwable) {

            throw new RuntimeException(throwable);
        }
    }
}











