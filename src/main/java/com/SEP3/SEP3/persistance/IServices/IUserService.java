package com.SEP3.SEP3.persistance.IServices;

import com.SEP3.SEP3.api.model.DTOs.TutorInformationDto;
import com.SEP3.SEP3.api.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User addUser(User user);
    Optional<User> getUserByUsername(String username);
    List<User> getAllUsers();
    User becomeTutor(User user, String course, String description);
    Optional<User> getTutorByUsername(String username);
    TutorInformationDto getTutor(String userName);
    User updateProfile (User user, String description, List<String> courses);

    boolean deleteAccount(int userId);
}
