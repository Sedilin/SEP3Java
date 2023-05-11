package com.SEP3.SEP3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonEncoding;

import java.io.Serializable;

public class User implements Serializable {
    private int Id;
    private String UserName;
    private String Password;
    private String UserType;

    private User(){}

    public User(String UserName, String Password) {
        this.UserName = UserName;
        this.Password = Password;
        UserType = "user";
    }

    public User(String UserName, String Password, String userType) {
        this.UserName = UserName;
        this.Password = Password;
        UserType = userType;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
