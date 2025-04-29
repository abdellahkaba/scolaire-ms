package com.isi.scolaire.student;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface StudentService {

    StudentResponse addStudent(StudentRequest request);
    StudentResponse getStudentById(Long id);
    List<StudentResponse> getAllStudents();
    StudentResponse updateStudent(StudentRequest request);
    void deleteStudentById(Long id);
    PageResponse<StudentResponse> listAllStudents(int page, int size);
}
