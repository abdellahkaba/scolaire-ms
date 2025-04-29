package com.isi.scolaire.teacher;


import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface TeacherService {

    TeacherResponse addTeacher(TeacherRequest request);
    TeacherResponse getTeacherById(Long id);
    List<TeacherResponse> getAllTeachers();
    TeacherResponse updateTeacher(TeacherRequest request);
    void deleteTeacherById(Long id);
    TeacherResponse assignCourseToTeacher(Long teacherId, Long courseId);

    PageResponse<TeacherResponse> listAllTeachers(int page, int size);
}
