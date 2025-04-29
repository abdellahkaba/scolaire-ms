package com.isi.scolaire.student;

import org.mapstruct.Mapper;

import java.util.List;

//@Mapper(componentModel = "spring", uses = {UserRepository.class})

@Mapper
public interface StudentMapper {

    Student toStudent(StudentRequest studentRequest);
//    @Mapping(source = "user.id", target = "userId")
    StudentResponse toStudentResponse(Student student);
    List<StudentResponse> toStudentResponseList(List<Student> students);

//    @Named("mapUserIdToUser")
//    static User mapUserIdToUser(Long userId) {
//        if (userId == null) {
//            return null;
//        }
//        User user = new User();
//        user.setId(userId);
//        return user;
//    }
}
