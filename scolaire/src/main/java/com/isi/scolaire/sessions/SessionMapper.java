package com.isi.scolaire.sessions;


import com.isi.scolaire.courses.Course;
import com.isi.scolaire.courses.CourseRepository;
import com.isi.scolaire.teacher.Teacher;
import com.isi.scolaire.teacher.TeacherRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses =
        {
                TeacherRepository.class,
                CourseRepository.class,
        })
public interface SessionMapper {

    @Mapping(source = "teacherId", target = "teacher", qualifiedByName = "mapTeacherIdToTeacher")
    @Mapping(source = "courseId", target = "course", qualifiedByName = "mapCourseIdToCourse")
    Session toSession(SessionRequest request);

    @Mapping(source = "teacher.id", target = "teacherId")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    @Mapping(source = "teacher.firstName", target = "teacherFirstName")
    @Mapping(source = "teacher.lastName", target = "teacherLastName")
    SessionResponse toSessionResponse(Session session);
    List<SessionResponse> toSessionResponseList(List<Session> sessions);

    @Named("mapTeacherIdToTeacher")
    static Teacher mapTeacherIdToTeacher(Long teacherId) {
        if (teacherId == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        return teacher;
    }

    @Named("mapCourseIdToCourse")
    static Course mapCourseIdToCourse(Long courseId) {
        if (courseId == null) {
            return null;
        }
        Course course = new Course();
        course.setId(courseId);
        return course;
    }
}
