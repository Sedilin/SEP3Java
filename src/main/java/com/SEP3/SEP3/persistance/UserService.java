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
       return users.addUser(user);
    }

   public Optional<User> getUserByUsername(String username) {
        return Optional.of(users.getByUsername(username));
   }
    public List<User> getAllUsers()
    {
        return users.getAllUsers();
    }


    public User becomeTutor(User user, String course, String description) {
        return users.becomeTutor(user, course, description);
    }

    public String getDescription(String userName) {
        return users.getDescription(userName);
    }
}
