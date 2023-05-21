package com.SEP3.SEP3.api.model.DTOs;
import com.SEP3.SEP3.api.model.User;

public class UserToTutorDto {
    private User User;
    private String Course;
    private String Description;

    public UserToTutorDto(User user, String course, String description) {
        this.User = user;
        Course = course;
        Description = description;
    }
    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
