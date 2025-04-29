package com.isi.scolaire.subjects;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface SubjectMapper {
    Subject toSubject(SubjectRequest request);
    SubjectResponse toSubjectResponse(Subject subject);
    List<SubjectResponse> toSubjectResponseList(List<Subject> subjects);
}
