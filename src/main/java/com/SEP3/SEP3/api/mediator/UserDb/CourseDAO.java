package com.SEP3.SEP3.api.mediator.UserDb;

import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;

import java.util.List;

public interface CourseDAO {

    List<UserToTutorDto> tutorByCourse (String course);
    List<String> getAllCourses();
}
