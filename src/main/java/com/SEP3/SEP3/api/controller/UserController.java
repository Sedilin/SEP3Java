package com.SEP3.SEP3.api.controller;

import com.SEP3.SEP3.api.model.DTOs.TutorInformationDto;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.IServices.IUserService;
import com.SEP3.SEP3.persistance.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody User user)
    {
        Optional<User> success = Optional.of(userService.addUser(user));
        if (success.isPresent())
        {
            return new ResponseEntity<>(success.get(), HttpStatus.OK);

        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> success = userService.getAllUsers();
        if (success != null)
        {
            return new ResponseEntity<>(success, HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/userName")
    public ResponseEntity<User> getUserByUsername( @RequestParam("userName") String userName)
    {

        Optional<User> existing = userService.getUserByUsername(userName);
        if (existing.isPresent())
            return new ResponseEntity<>(existing.get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/newTutor")
    public ResponseEntity<User> becomeTutor(@RequestBody UserToTutorDto dto)
    {
        Optional<User> success = Optional.of(userService.becomeTutor(dto.getUser(), dto.getCourse(), dto.getDescription()));
        if (success.isPresent())
        {
            return new ResponseEntity<>(success.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tutorByUsername")
    public ResponseEntity<User> getTutorByUsername(@RequestParam("userName") String username) {
        Optional<User> existing = userService.getTutorByUsername(username);
        if (existing.isPresent()) {
                return new ResponseEntity<>(existing.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/tutor")
    public ResponseEntity<TutorInformationDto> GetTutorAsync( @RequestParam("userName") String userName)
    {
        Optional<TutorInformationDto> existing = Optional.of(userService.getTutor(userName));
        if (existing.isPresent())
            return new ResponseEntity<>(existing.get(), HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody TutorInformationDto dto) {
        User updatedUser = userService.updateProfile(dto.getUser(), dto.getDescription(), dto.getCourses());
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("removeAccount")
    public ResponseEntity deleteAccount(@RequestParam int userId)
    {
        boolean isCompleted = userService.deleteAccount(userId);
        if (isCompleted == true)
        {
            return new ResponseEntity(HttpStatus.OK);
        }
        else {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
