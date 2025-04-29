package com.isi.scolaire.teacher;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//@Mapper(componentModel = "spring", uses = {UserRepository.class})
@Mapper
public interface TeacherMapper {

    Teacher toTeacher(TeacherRequest TeacherRequest);
//    @Mapping(source = "user.id", target = "userId")
    TeacherResponse toTeacherResponse(Teacher Teacher);
    List<TeacherResponse> toTeacherResponseList(List<Teacher> Teachers);


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
