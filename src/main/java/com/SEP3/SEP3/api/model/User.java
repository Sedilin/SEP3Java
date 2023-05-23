package com.SEP3.SEP3.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private int id;
    private String userName;
    private String password;
    private String userType;


    public User(int id, String userName, String password, String userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
        this.id = id;
    }

    @JsonIgnore
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        userType = "user";
    }

    @JsonIgnore
    public User(String userName, String password, String userType) {
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    @JsonProperty("Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("UserName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    @JsonProperty("Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @JsonProperty("UserType")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
