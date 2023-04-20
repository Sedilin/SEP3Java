package com.SEP3.SEP3.api.controller;

import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> addNewUser(@RequestBody User user)
    {
        User success = userService.addUser(user);
        if (success != null)
        {
            return new ResponseEntity<>(success, HttpStatus.OK);
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

    @GetMapping()
    public ResponseEntity<User> getUserByUsername( String username)
    {
        List<User> users = getAllUsers().getBody();
        for (User user: users)
        {
            if (user.getUserName().equals(username))
            {
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
