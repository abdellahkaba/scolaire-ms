package com.isi.scolaire.sessions.impl;

import com.isi.scolaire.courses.Course;
import com.isi.scolaire.courses.CourseRepository;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.sessions.*;
import com.isi.scolaire.teacher.Teacher;
import com.isi.scolaire.teacher.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessionServiceImplTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Test
    void addSessionOK() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(getTeacher()));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(getCourse()));
        when(sessionMapper.toSession(any())).thenReturn(getSession());
        when(sessionRepository.save(any())).thenReturn(getSession());
        when(sessionMapper.toSessionResponse(any())).thenReturn(getSessionResponse());

        SessionResponse response = sessionService.addSession(getSessionRequest());

        assertNotNull(response);
        assertEquals("Session1", response.getName());
    }

    @Test
    void addSessionKO_TeacherNotFound() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("teacher.notfound"), any(), any(Locale.class)))
                .thenReturn("Teacher not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sessionService.addSession(getSessionRequest()));
        assertEquals("Teacher not found", exception.getMessage());
    }

    @Test
    void addSessionKO_CourseNotFound() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(getTeacher()));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("course.notfound"), any(), any(Locale.class)))
                .thenReturn("Course not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sessionService.addSession(getSessionRequest()));
        assertEquals("Course not found", exception.getMessage());
    }

    @Test
    void getSessionByIdOK() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(getSession()));
        when(sessionMapper.toSessionResponse(any())).thenReturn(getSessionResponse());

        SessionResponse response = sessionService.getSessionById(1L);

        assertNotNull(response);
        assertEquals("Session1", response.getName());
    }

    @Test
    void getSessionByIdKO() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("session.notfound"), any(), any(Locale.class)))
                .thenReturn("Session not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sessionService.getSessionById(1L));
        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    void deleteSessionByIdOK() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(getSession()));

        sessionService.deleteSessionById(1L);

        verify(sessionRepository, times(1)).delete(any());
    }

    @Test
    void deleteSessionByIdKO() {
        when(sessionRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("session.notfound"), any(), any(Locale.class)))
                .thenReturn("Session not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sessionService.deleteSessionById(1L));
        assertEquals("Session not found", exception.getMessage());
    }

    private Session getSession() {
        Session session = new Session();
        session.setId(1L);
        return session;
    }

    private SessionRequest getSessionRequest() {
        SessionRequest request = new SessionRequest();
        request.setId(1L);
        request.setName("Session1");
        request.setDescription("Description session1");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        request.setBigInDate(LocalDateTime.parse("2024/09/09 10:00:00", formatter));
        request.setEndDate(LocalDateTime.parse("2025-03-03T15:30:00"));
        request.setTeacherId(1L);
        request.setCourseId(1L);
        request.setArchive(false);
        return request;
    }
    private SessionResponse getSessionResponse() {
       SessionResponse response = new SessionResponse();
       response.setName("Session1");
       response.setDescription("Description session1");
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
       response.setBigInDate(LocalDateTime.parse("2024/09/09 10:00:00", formatter));
       response.setEndDate(LocalDateTime.parse("2025-03-03T15:30:00"));
       response.setTeacherId(1L);
       response.setCourseId(1L);
       response.setArchive(false);
       return response;
    }

    private Teacher getTeacher() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        return teacher;
    }
    private Course getCourse() {
        Course course = new Course();
        course.setId(1L);
        return course ;
    }

}
