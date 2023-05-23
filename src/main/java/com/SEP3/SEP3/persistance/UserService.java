package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.mediator.UserDb.UserDAO;
import com.SEP3.SEP3.api.mediator.UserDb.UserDAOImpl;
import com.SEP3.SEP3.api.model.DTOs.TutorInformationDto;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.IServices.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

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



    public Optional<User> getTutorByUsername(String username) {
        User tutor = users.tutorByUsername(username);
        if (tutor != null) {
            return Optional.of(tutor);
        } else {
            return Optional.empty();
        }
    }

    public TutorInformationDto getTutor(String userName) {
        return users.getTutor(userName);
    }


    public User updateProfile (User user, String description, List<String> courses) {
        return users.updateProfile(user, description, courses);
    }

    @Override
    public boolean deleteAccount(int userId) {
        return users.deleteAccount(userId);
    }
}
