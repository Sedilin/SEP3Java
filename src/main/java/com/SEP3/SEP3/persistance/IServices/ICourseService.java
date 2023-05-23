package com.SEP3.SEP3.persistance.IServices;

import com.SEP3.SEP3.api.model.DTOs.UserToTutorDto;
import com.SEP3.SEP3.api.model.User;
import java.util.List;

public interface ICourseService {
     List<UserToTutorDto> getTutorByCourse(String course);
     List<String> geAllCourses();
     UserToTutorDto deleteCourse (User user, String course, String description);
}
