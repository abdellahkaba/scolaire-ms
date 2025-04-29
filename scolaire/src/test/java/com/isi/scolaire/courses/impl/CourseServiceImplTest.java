package com.isi.scolaire.courses.impl;

import com.isi.scolaire.academieYear.AcademieYear;
import com.isi.scolaire.academieYear.AcademieYearRepository;
import com.isi.scolaire.classes.Classe;
import com.isi.scolaire.classes.ClasseRepository;
import com.isi.scolaire.courses.*;
import com.isi.scolaire.halfYearly.HalfYearly;
import com.isi.scolaire.halfYearly.HalfYearlyRepository;
import com.isi.scolaire.subjects.Subject;
import com.isi.scolaire.subjects.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private  CourseRepository courseRepository;
    @Mock
    private  CourseMapper courseMapper;
    @Mock
    private  ClasseRepository classeRepository;
    @Mock
    private  SubjectRepository subjectRepository;
    @Mock
    private  HalfYearlyRepository halfYearlyRepository;
    @Mock
    private  AcademieYearRepository academieYearRepository;
    @Mock
    private  MessageSource messageSource;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Classe getClasse(){
        Classe classe = new Classe();
        classe.setId(1L);
        return classe;
    }
    private Subject getSubject(){
        Subject subject = new Subject();
        subject.setId(1L);
        return subject;
    }
    private HalfYearly getHalfYearly(){
        HalfYearly halfYearly = new HalfYearly();
        halfYearly.setId(1L);
        return halfYearly;
    }
    private AcademieYear getAcademieYear(){
        AcademieYear academieYear = new AcademieYear();
        academieYear.setId(1L);
        return academieYear;
    }

    private CourseResponse getCourseResponse(){
        CourseResponse response = new CourseResponse();
        response.setId(1L);
        response.setName("Course Algèbre");
        response.setClasseId(1L);
        response.setSubjectId(1L);
        response.setAcademieYearId(1L);
        response.setHalfYearlyId(1L);
        return response;
    }

    private CourseRequest getCourseRequest(){
        CourseRequest request = new CourseRequest();
        request.setId(1L);
        request.setName("Course Algèbre");
        request.setDescription("Description d'algèbre");
        request.setSubjectId(1L);
        request.setHalfYearlyId(1L);
        request.setAcademieYearId(1L);
        request.setClasseId(1L);
        request.setArchive(false);
        return request;
    }

    private Course getCourse(){
        Course course = new Course();
        course.setId(1L);
        course.setName("Course Algèbre");
        course.setDescription("Description d'algèbre");
        course.setArchive(false);
        Classe classe = new Classe();
        course.setClasse(classe);
        Subject subject = new Subject();
        course.setSubject(subject);
        HalfYearly halfYearly = new HalfYearly();
        course.setHalfYearly(halfYearly);
        AcademieYear academieYear = new AcademieYear();
        course.setAcademieYear(academieYear);
        return course;
    }


    @Test
    void addCourse(){
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(getSubject()));
        when(halfYearlyRepository.findById(anyLong())).thenReturn(Optional.of(getHalfYearly()));
        when(academieYearRepository.findById(anyLong())).thenReturn(Optional.of(getAcademieYear()));

        when(courseMapper.toCourse(any())).thenReturn(this.getCourse());
        when(courseRepository.save(any())).thenReturn(this.getCourse());
        when(courseMapper.toCourseResponse(any())).thenReturn(this.getCourseResponse());

        CourseResponse response = courseService.addCourse(getCourseRequest());
        assertEquals("Course Algèbre", response.getName());
    }

}