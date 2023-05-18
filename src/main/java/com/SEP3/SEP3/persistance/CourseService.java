package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.mediator.CourseDb.CourseDAO;
import com.SEP3.SEP3.api.mediator.CourseDb.CourseDAOImpl;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {


    CourseDAO courses;

    public CourseService() {
        courses = new CourseDAOImpl();
    }
    public List<UserToTutorDto> getTutorByCourse (String course) {
        return courses.tutorByCourse(course);
    }
    public List<String> geAllCourses() {return courses.getAllCourses();}
}
