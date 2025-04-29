package com.isi.scolaire.courses;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface CourseService {

    CourseResponse addCourse(CourseRequest request);
    CourseResponse getCourseById(Long id);
    List<CourseResponse> getAllCourses();
    CourseResponse updateCourse(CourseRequest request);
    void deleteCourseById(Long id);
    PageResponse<CourseResponse> listAllCourses(int page, int size);
}
