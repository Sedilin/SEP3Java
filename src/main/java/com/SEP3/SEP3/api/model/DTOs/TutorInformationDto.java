package com.SEP3.SEP3.api.model.DTOs;

import com.SEP3.SEP3.api.model.User;

import java.util.ArrayList;
import java.util.List;

public class TutorInformationDto {
    private User User;
    private List<String> Courses;
    private String Description;

    public TutorInformationDto(User user, String description) {
        Courses = new ArrayList<>();
        this.User = user;
        Description = description;
    }

    public List<String> getCourses() {
        return Courses;
    }

    public void addCourse(String course) {
        Courses.add(course);
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setCourses(List<String> courses){Courses = courses;}
}
