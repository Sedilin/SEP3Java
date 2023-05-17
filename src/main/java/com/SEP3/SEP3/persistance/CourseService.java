package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.mediator.UserDb.CourseDAO;
import com.SEP3.SEP3.api.mediator.UserDb.CourseDAOImpl;
import com.SEP3.SEP3.api.mediator.UserDb.UserDAO;
import com.SEP3.SEP3.api.mediator.UserDb.UserDAOImpl;
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
