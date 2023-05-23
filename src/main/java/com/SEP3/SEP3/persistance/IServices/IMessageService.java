package com.SEP3.SEP3.persistance.IServices;

import com.SEP3.SEP3.api.model.User;
import org.springframework.stereotype.Service;

@Service
public interface IMessageService {
    boolean archiveMessage(User sender, String message, User receiver);
}
