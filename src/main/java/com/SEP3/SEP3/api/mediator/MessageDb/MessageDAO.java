package com.SEP3.SEP3.api.mediator.MessageDb;

import com.SEP3.SEP3.api.model.DTOs.MessageDto;
import com.SEP3.SEP3.api.model.User;

import java.util.List;

public interface MessageDAO {
    boolean archiveMessage(User sender, String message, User receiver);
    List<MessageDto> showMessages(int loggedUserId, int otherUserId);
    List<User> showConversation(int loggedUserId);
    boolean deleteConversation(int loggedUserId, int otherUserId);

}
