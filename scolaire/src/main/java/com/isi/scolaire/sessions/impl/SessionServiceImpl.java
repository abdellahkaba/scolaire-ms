package com.isi.scolaire.sessions.impl;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.courses.CourseRepository;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.exception.InvalidDateException;
import com.isi.scolaire.sessions.*;
import com.isi.scolaire.teacher.TeacherRepository;
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
@Setter
@Getter
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final MessageSource messageSource;

    @Override
    public SessionResponse addSession(SessionRequest request) {
        var teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("teacher.notfound", new Object[]{request.getTeacherId()}, Locale.getDefault())));
        var course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("course.notfound", new Object[]{request.getCourseId()}, Locale.getDefault())));

        if (request.getEndDate().isBefore(request.getBigInDate())){
            throw new InvalidDateException(messageSource.getMessage("bigInDate.endDate.superior", new Object[]{request.getEndDate()}, Locale.getDefault()));
        }
        Session session = sessionMapper.toSession(request);
        session.setTeacher(teacher);
        session.setCourse(course);
        var savedSession = sessionRepository.save(session);
        return sessionMapper.toSessionResponse(savedSession);
    }

    @Override
    public SessionResponse getSessionById(Long id) {
        return sessionRepository.findById(id)
                .map(sessionMapper::toSessionResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("session.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<SessionResponse> getAllSessions() {
        return sessionMapper.toSessionResponseList(sessionRepository.findAll());
    }

    @Override
    public SessionResponse updateSession(SessionRequest request) {
        var teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("teacher.notfound", new Object[]{request.getTeacherId()}, Locale.getDefault())));
        var course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("course.notfound", new Object[]{request.getCourseId()}, Locale.getDefault())));
        if (request.getEndDate().isBefore(request.getBigInDate())){
            throw new InvalidDateException(messageSource.getMessage("bigInDate.endDate.superior", new Object[]{request.getEndDate()}, Locale.getDefault()));
        }
        var session = sessionRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("session.notfound", new Object[]{request.getId()}, Locale.getDefault())));

        session.setTeacher(teacher);
        session.setCourse(course);
        session.setName(request.getName());
        session.setDescription(request.getDescription());
        session.setBigInDate(request.getBigInDate());
        session.setEndDate(request.getEndDate());
        session.setArchive(request.isArchive());
        return sessionMapper.toSessionResponse(sessionRepository.save(session));
    }
    @Override
    public void deleteSessionById(Long id){
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("session.notfound", new Object[]{id}, Locale.getDefault())));
        sessionRepository.delete(session);
    }

    @Override
    public PageResponse<SessionResponse> listAllSessions(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Session> sessions = sessionRepository.findAll(pageable);
        List<SessionResponse> responses = sessions.stream()
                .map(sessionMapper::toSessionResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                sessions.getNumber(),
                sessions.getSize(),
                sessions.getTotalElements(),
                sessions.getTotalPages(),
                sessions.isFirst(),
                sessions.isLast()
        );
    }
}
