package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.mediator.UserDb.UserDAO;
import com.SEP3.SEP3.api.mediator.UserDb.UserDAOImpl;
import com.SEP3.SEP3.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    UserDAO users;

    public UserService() {
        users = new UserDAOImpl();
    }


    public User addUser(User user)
    {
        users.addUser(user);
        return user;
    }
   public User getUserByUsername(String username) {
        return users.getByUsername();
   }
    public List<User> getAllUsers()
    {
        return users.getAllUsers();
    }



}
