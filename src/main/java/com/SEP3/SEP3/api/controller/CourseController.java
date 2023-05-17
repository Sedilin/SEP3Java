package com.SEP3.SEP3.api.controller;

import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.persistance.CourseService;
import com.SEP3.SEP3.persistance.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping("/tutorByCourse")
    public ResponseEntity<List<UserToTutorDto>> tutorByCourse(@RequestParam("name") String course) {
        List<UserToTutorDto> tutors = courseService.getTutorByCourse(course);
        if (!tutors.isEmpty()) {
            return new ResponseEntity<>(tutors, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping()
    public ResponseEntity<List<String>> getAllCourses()
    {
        List<String> courses = courseService.geAllCourses();
        if (!courses.isEmpty())
        {
            return new ResponseEntity<>(courses, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}