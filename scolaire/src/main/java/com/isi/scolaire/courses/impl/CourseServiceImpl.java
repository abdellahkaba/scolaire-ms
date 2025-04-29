package com.isi.scolaire.courses.impl;


import com.isi.scolaire.academieYear.AcademieYearRepository;
import com.isi.scolaire.classes.ClasseRepository;
import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.courses.*;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.halfYearly.HalfYearlyRepository;
import com.isi.scolaire.subjects.SubjectRepository;
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
@AllArgsConstructor
@Getter
@Setter
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final ClasseRepository classeRepository;
    private final SubjectRepository subjectRepository;
    private final HalfYearlyRepository halfYearlyRepository;
    private final AcademieYearRepository academieYearRepository;
    private final MessageSource messageSource;

    @Override
    public CourseResponse addCourse(CourseRequest request) {
        var classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("classe.notfound",
                        new Object[]{request.getClasseId()},
                        Locale.getDefault())));

        var subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("subject.notfound", new Object[]{request.getSubjectId()}, Locale.getDefault())));
        var halfYearly = halfYearlyRepository.findById(request.getHalfYearlyId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("halfYearly.notfound", new Object[]{request.getHalfYearlyId()}, Locale.getDefault())));

        /**
         * Pour l'academie optionnel
         */
        var academieYear = request.getAcademieYearId() != null
                ? academieYearRepository.findById(request.getAcademieYearId()).orElse(null)
                : null;
        if (courseRepository.findByNameAndClasseIdAndSubjectIdAndHalfYearlyIdAndAcademieYearId(
                request.getName(),
                request.getClasseId(),
                request.getSubjectId(),
                request.getHalfYearlyId(),
                request.getAcademieYearId()).isPresent())
        {
            throw new EntityExistsException(messageSource.getMessage("course.classe.subject.halfYearly.academieYear.exists",
                    new Object[]{request.getClasseId(),
                            request.getSubjectId(),
                            request.getHalfYearlyId()},
                    Locale.getDefault()));
        }
        Course course = courseMapper.toCourse(request);
        course.setSubject(subject);
        course.setHalfYearly(halfYearly);
        course.setClasse(classe);
        course.setAcademieYear(academieYear);
        var savedCourse = courseRepository.save(course);
        return courseMapper.toCourseResponse(savedCourse);
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(courseMapper::toCourseResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("course.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        return courseMapper.toCourseResponseList(courseRepository.findAll());
    }

    @Override
    public CourseResponse updateCourse(CourseRequest request) {
        var classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[]{request.getClasseId()}, Locale.getDefault())));
        var subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("subject.notfound", new Object[]{request.getSubjectId()}, Locale.getDefault())));
        var halfYearly = halfYearlyRepository.findById(request.getHalfYearlyId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("halfYearly.notfound", new Object[]{request.getHalfYearlyId()}, Locale.getDefault())));


        var existingCourse = courseRepository.findByNameAndClasseIdAndSubjectIdAndHalfYearlyIdAndAcademieYearId(
                request.getName(),
                request.getClasseId(),
                request.getSubjectId(),
                request.getHalfYearlyId(),
                request.getAcademieYearId());
        if (existingCourse.isPresent() && !existingCourse.get().getId().equals(request.getId())) {
            throw new EntityExistsException(messageSource.getMessage("course.classe.subject.halfYearly.academieYear.exists", new Object[]{request.getClasseId(), request.getSubjectId(), request.getHalfYearlyId()}, Locale.getDefault()));
        }
        var course = courseRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("course.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        var academieYear = academieYearRepository.findById(request.getAcademieYearId())
                        .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("academieYear.notfound", new Object[]{request.getAcademieYearId()}, Locale.getDefault())));
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setSubject(subject);
        course.setHalfYearly(halfYearly);
        course.setClasse(classe);
        course.setAcademieYear(academieYear);
        course.setArchive(request.isArchive());
        var updatedCourse = courseRepository.save(course);
        return courseMapper.toCourseResponse(updatedCourse);
    }
    @Override
    public void deleteCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("course.notfound", new Object[]{id}, Locale.getDefault())));
        courseRepository.delete(course);
    }
    @Override
    public PageResponse<CourseResponse> listAllCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courses = courseRepository.findAll(pageable);
        List<CourseResponse> responses = courses.stream()
                .map(courseMapper::toCourseResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                courses.getNumber(),
                courses.getSize(),
                courses.getTotalElements(),
                courses.getTotalPages(),
                courses.isFirst(),
                courses.isLast()
        );
    }
}