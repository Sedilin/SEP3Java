package com.SEP3.SEP3.persistance.IServices;

import com.SEP3.SEP3.api.model.DTOs.MessageDto;
import com.SEP3.SEP3.api.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMessageService {
    boolean archiveMessage(User sender, String message, User receiver);
    List<MessageDto> showMessages(int loggedUserId, int otherUserId);
    List<User> showConversation(int loggedUserId);
    boolean deleteConversations (int loggedUserId, int otherUserId);
}
