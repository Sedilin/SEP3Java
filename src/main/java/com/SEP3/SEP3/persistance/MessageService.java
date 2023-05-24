package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.mediator.MessageDb.MessageDAO;
import com.SEP3.SEP3.api.mediator.MessageDb.MessageDAOImpl;
import com.SEP3.SEP3.api.model.DTOs.MessageDto;
import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.IServices.IMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {

    private MessageDAO message;

    public MessageService() {
        message = new MessageDAOImpl();
    }

    @Override
    public boolean archiveMessage(User sender, String message, User receiver) {
        return this.message.archiveMessage(sender, message, receiver);
    }

    @Override
    public List<MessageDto> showMessages(int loggedUserId, int otherUserId) {
        return message.showMessages(loggedUserId, otherUserId);
    }

    @Override
    public List<User> showConversation(int loggedUserId) {
        return message.showConversation(loggedUserId);
    }

    @Override
    public boolean deleteConversations(int loggedUserId, int otherUserId) {
        return this.message.deleteConversation( loggedUserId, otherUserId);
    }
}
