package com.SEP3.SEP3.api.mediator.MessageDb;

import com.SEP3.SEP3.api.model.User;

public interface MessageDAO {
    boolean archiveMessage(User sender, String message, User receiver);
}
