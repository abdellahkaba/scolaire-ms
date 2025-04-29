package com.isi.scolaire.teacher.impl;

import com.isi.scolaire.courses.Course;
import com.isi.scolaire.courses.CourseRepository;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.teacher.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    private Teacher teacher;
    private TeacherRequest teacherRequest;
    private TeacherResponse teacherResponse;

    @BeforeEach
    void setUp() {
        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setEmailPerso("john.doe@example.com");
        teacher.setEmailPro("john.doe@school.com");
        teacher.setPhoneNumber("123456789");

        teacherRequest = new TeacherRequest();
        teacherRequest.setId(1L);
        teacherRequest.setFirstName("John");
        teacherRequest.setLastName("Doe");
        teacherRequest.setEmailPerso("john.doe@example.com");
        teacherRequest.setEmailPro("john.doe@school.com");
        teacherRequest.setPhoneNumber("123456789");

        teacherResponse = new TeacherResponse();
        teacherResponse.setId(1L);
        teacherResponse.setFirstName("John");
        teacherResponse.setLastName("Doe");
    }

    @Test
    void addTeacher_Success() {
        when(teacherRepository.findByEmailPerso(teacherRequest.getEmailPerso())).thenReturn(Optional.empty());
        when(teacherRepository.findByEmailPro(teacherRequest.getEmailPro())).thenReturn(Optional.empty());
        when(teacherRepository.findByPhoneNumber(teacherRequest.getPhoneNumber())).thenReturn(Optional.empty());
        when(teacherMapper.toTeacher(teacherRequest)).thenReturn(teacher);
        when(teacherRepository.save(teacher)).thenReturn(teacher);
        when(teacherMapper.toTeacherResponse(teacher)).thenReturn(teacherResponse);

        TeacherResponse response = teacherService.addTeacher(teacherRequest);

        assertNotNull(response);
        assertEquals(teacherResponse.getId(), response.getId());
    }

    @Test
    void addTeacher_ThrowsEntityExistsException() {
        when(teacherRepository.findByEmailPerso(teacherRequest.getEmailPerso())).thenReturn(Optional.of(teacher));
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Email déjà utilisé");

        assertThrows(EntityExistsException.class, () -> teacherService.addTeacher(teacherRequest));
    }

    @Test
    void getTeacherById_Success() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherMapper.toTeacherResponse(teacher)).thenReturn(teacherResponse);

        TeacherResponse response = teacherService.getTeacherById(1L);

        assertNotNull(response);
        assertEquals(teacherResponse.getId(), response.getId());
    }

    @Test
    void getTeacherById_ThrowsEntityNotFoundException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Enseignant introuvable");

        assertThrows(EntityNotFoundException.class, () -> teacherService.getTeacherById(1L));
    }

    @Test
    void getAllTeachers_Success() {
        when(teacherRepository.findAll()).thenReturn(List.of(teacher));
        when(teacherMapper.toTeacherResponseList(any())).thenReturn(List.of(teacherResponse));

        List<TeacherResponse> responses = teacherService.getAllTeachers();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void updateTeacher_Success() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(teacherRepository.findByEmailPerso(teacherRequest.getEmailPerso())).thenReturn(Optional.of(teacher));
        when(teacherRepository.findByEmailPro(teacherRequest.getEmailPro())).thenReturn(Optional.of(teacher));
        when(teacherRepository.findByPhoneNumber(teacherRequest.getPhoneNumber())).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
        when(teacherMapper.toTeacherResponse(teacher)).thenReturn(teacherResponse);

        TeacherResponse response = teacherService.updateTeacher(teacherRequest);

        assertNotNull(response);
        assertEquals(teacherResponse.getId(), response.getId());
    }

    @Test
    void updateTeacher_ThrowsEntityNotFoundException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Enseignant introuvable");

        assertThrows(EntityNotFoundException.class, () -> teacherService.updateTeacher(teacherRequest));
    }

    @Test
    void deleteTeacherById_Success() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));

        assertDoesNotThrow(() -> teacherService.deleteTeacherById(1L));

        verify(teacherRepository, times(1)).delete(teacher);
    }


    @Test
    void deleteTeacherById_ThrowsEntityNotFoundException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Enseignant introuvable");

        assertThrows(EntityNotFoundException.class, () -> teacherService.deleteTeacherById(1L));
    }

//    @Test
//    void assignCourseToTeacher_Success() {
//        Course course = new Course();
//        course.setId(2L);
//
//        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
//        when(courseRepository.findById(2L)).thenReturn(Optional.of(this.getCourse()));
//        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);
//        when(teacherMapper.toTeacherResponse(teacher)).thenReturn(teacherResponse);
//
//        TeacherResponse response = teacherService.assignCourseToTeacher(1L, 2L);
//
//        assertNotNull(response);
//        verify(teacherRepository, times(1)).save(any(Teacher.class));
//    }

    @Test
    void assignCourseToTeacher_ThrowsEntityNotFoundException() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());
        when(messageSource.getMessage(any(), any(), any())).thenReturn("Enseignant introuvable");

        assertThrows(EntityNotFoundException.class, () -> teacherService.assignCourseToTeacher(1L, 2L));
    }

    private Course getCourse() {
        Course course = new Course();
        course.setId(1L);
        return course ;
    }
}