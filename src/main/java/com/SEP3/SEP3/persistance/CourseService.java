package com.SEP3.SEP3.persistance;

import com.SEP3.SEP3.api.mediator.CourseDb.CourseDAO;
import com.SEP3.SEP3.api.mediator.CourseDb.CourseDAOImpl;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;
import com.SEP3.SEP3.persistance.IServices.ICourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {
    CourseDAO courses;

    public CourseService() {
        courses = new CourseDAOImpl();
    }

    public List<UserToTutorDto> getTutorByCourse(String course) {
        return courses.tutorByCourse(course);
    }

    public List<String> geAllCourses() {
        return courses.getAllCourses();
    }

    public UserToTutorDto deleteCourse (User user, String course, String description) {
        return courses.deleteCourse(user, course, description);
    }
}


