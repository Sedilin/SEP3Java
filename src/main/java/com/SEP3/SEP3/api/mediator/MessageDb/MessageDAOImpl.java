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

    @Override
    public List<User> showConversation(int loggedUserId) {
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("SELECT *\n" +
                    "FROM Users\n" +
                    "WHERE id IN (SELECT DISTINCT sender_id\n" +
                    "             FROM Messages\n" +
                    "             WHERE recipient_id == ?) OR id IN (SELECT DISTINCT recipient_id\n" +
                    "                                          FROM Messages\n" +
                    "                                          WHERE sender_id == ?);");

            statement.setInt(1, loggedUserId);
            statement.setInt(2, loggedUserId);

            ResultSet resultSet = statement.executeQuery();

            List<User> users = new ArrayList<>();

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String userType = resultSet.getString("userType");

                users.add(new User(id, username, password, userType));
            }

            return users;

        } catch (SQLException throwable) {

            throw new RuntimeException(throwable);
        }
    }

    @Override
    public boolean deleteConversation(int loggedUserId, int otherUserId) {
        try (Connection connection = DbConnection.getConnection()){
            // Delete messages from the sender's side
            String deleteSenderQuery = "DELETE FROM Messages WHERE (sender_id = ? AND recipient_id = ?) OR (sender_id = ? AND recipient_id = ?)";
            PreparedStatement deleteSenderStmt = connection.prepareStatement(deleteSenderQuery);
            deleteSenderStmt.setInt(1, loggedUserId);
            deleteSenderStmt.setInt(2, otherUserId);
            deleteSenderStmt.setInt(3, otherUserId);
            deleteSenderStmt.setInt(4, loggedUserId);
            deleteSenderStmt.executeUpdate();

            return true; // Deletion successful
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Deletion failed
        }
    }


}











