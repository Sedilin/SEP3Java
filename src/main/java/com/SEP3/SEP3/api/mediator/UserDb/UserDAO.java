package com.SEP3.SEP3.api.mediator.UserDb;

import com.SEP3.SEP3.api.model.DTOs.TutorInformationDto;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserDAO {
    User addUser(User user);
    boolean isUserNameExistent(String username);
    List<User> getAllUsers();

    User getByUsername(String username);

    User becomeTutor(User user, String course, String description);


    User tutorByUsername (String username);

    TutorInformationDto getTutor(String userName);

     User updateProfile(User user, String description, List<String> course);


    boolean deleteAccount(int userId);
}
