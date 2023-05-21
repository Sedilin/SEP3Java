package com.SEP3.SEP3.api.mediator.CourseDb;

import com.SEP3.SEP3.api.model.DTOs.TutorInformationDto;
import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;

import java.util.List;

public interface CourseDAO {

    List<UserToTutorDto> tutorByCourse (String course);
    List<String> getAllCourses();

    UserToTutorDto deleteCourse(User user, String course, String description);
}
