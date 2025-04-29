package com.isi.scolaire.teacher.impl;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.courses.CourseRepository;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.teacher.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Service
@Getter
@Setter
@AllArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final CourseRepository courseRepository;
    private final MessageSource messageSource;

    @Override
    public TeacherResponse addTeacher(TeacherRequest request) {
        if (teacherRepository.findByEmailPerso(request.getEmailPerso()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("emailPerso.exists", new
                    Object[]{request.getEmailPerso()}, Locale.getDefault()));
        }
        if (teacherRepository.findByEmailPro(request.getEmailPro()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("emailPro.exists", new
                    Object[]{request.getEmailPerso()}, Locale.getDefault()));
        }
        if (teacherRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("phoneNumber.exists", new
                    Object[]{request.getPhoneNumber()}, Locale.getDefault()));
        }

        Teacher teacher = teacherMapper.toTeacher(request);
        var savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponse(savedTeacher);
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toTeacherResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("teacher.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<TeacherResponse> getAllTeachers() {
        return teacherMapper.toTeacherResponseList(teacherRepository.findAll());
    }

    @Override
    public TeacherResponse updateTeacher(TeacherRequest request) {
        var existingTeacher = teacherRepository.findByEmailPerso(request.getEmailPerso());
        if (existingTeacher.isPresent() && !existingTeacher.get().getId().equals(request.getId())) {
            throw new EntityExistsException(messageSource.getMessage(
                    "emailPerso.exists", new Object[]{request.getEmailPerso()}, Locale.getDefault()));
        }
        var existingTeacherByEmailPro = teacherRepository.findByEmailPro(request.getEmailPro());
        if (existingTeacherByEmailPro.isPresent() && !existingTeacherByEmailPro.get().getId().equals(request.getId())) {
            throw new EntityExistsException(messageSource.getMessage(
                    "emailPro.exists", new Object[]{request.getEmailPro()}, Locale.getDefault()));
        }

        var existingTeacherByPhone = teacherRepository.findByPhoneNumber(request.getPhoneNumber());
        if (existingTeacherByPhone.isPresent() && !existingTeacherByPhone.get().getId().equals(request.getId())) {
            throw new EntityExistsException(messageSource.getMessage(
                    "phoneNumber.exists", new Object[]{request.getPhoneNumber()}, Locale.getDefault()));
        }

        var teacher = teacherRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("teacher.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setEmailPerso(request.getEmailPerso());
        teacher.setEmailPro(request.getEmailPro());
        teacher.setPhoneNumber(request.getPhoneNumber());
        teacher.setAddress(request.getAddress());
        var updateTeacher = teacherRepository.save(teacher);
        return teacherMapper.toTeacherResponse(updateTeacher);
    }

    @Override
    public void deleteTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("teacher.notfound", new Object[]{id}, Locale.getDefault() )));
        teacherRepository.delete(teacher);
    }

    @Override
    public TeacherResponse assignCourseToTeacher(Long teacherId, Long courseId) {
        var teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("teacher.notfound", new Object[]{teacherId}, Locale.getDefault() )));
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("course.notfound", new Object[]{courseId}, Locale.getDefault() )));
        teacher.getCourses().add(course);
        course.getTeachers().add(teacher);
        return teacherMapper.toTeacherResponse(teacherRepository.save(teacher));
    }

    @Override
    public PageResponse<TeacherResponse> listAllTeachers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Teacher> teachers = teacherRepository.findAll(pageable);
        List<TeacherResponse> responses = teachers.stream()
                .map(teacherMapper::toTeacherResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                teachers.getNumber(),
                teachers.getSize(),
                teachers.getTotalElements(),
                teachers.getTotalPages(),
                teachers.isFirst(),
                teachers.isLast()
        );
    }
}
