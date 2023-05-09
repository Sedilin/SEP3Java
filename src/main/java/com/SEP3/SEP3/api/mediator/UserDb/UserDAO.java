package com.SEP3.SEP3.api.mediator.UserDb;

import com.SEP3.SEP3.api.model.User;

import java.util.List;

public interface UserDAO {
    boolean addUser(User user);
    boolean isUserNameExistent(String username);
    List<User> getAllUsers();
}
