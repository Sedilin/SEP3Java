package com.SEP3.SEP3.api.controller;

import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.persistance.IServices.ICourseService;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {

    private ICourseService courseService;

    @Autowired
    public CourseController(ICourseService courseService) {
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

    @DeleteMapping("/delete/{course_id}")
    public ResponseEntity<UserToTutorDto> deleteCourse(@RequestParam("course_id") UserToTutorDto dto)
    {
        Optional<UserToTutorDto> success = Optional.of(courseService.deleteCourse(dto.getUser(), dto.getCourse(), dto.getDescription()));
        if (success.isPresent())
        {
            return new ResponseEntity<>(success.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }



}