package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.mediator.MessageDb.MessageDAO;
import com.SEP3.SEP3.api.mediator.MessageDb.MessageDAOImpl;
import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.IServices.IMessageService;
import org.springframework.stereotype.Service;

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
}
