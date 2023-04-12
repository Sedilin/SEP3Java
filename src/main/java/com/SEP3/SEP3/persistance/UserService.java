package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    public Optional<User> getUserById(long id)
    {
        Optional<User> existing = Optional.empty();
        for (User user: users)
        {
            if (user.getId() == id)
            {
                existing = Optional.of(user);
            }
        }
        return existing;
    }

    public User addUser(User user)
    {
        Optional<User> existing = getUserById(user.getId());
        if (existing.isPresent())
        {
            return null;
        }
        else
        {
            users.add(user);
            return user;
        }
    }

}
