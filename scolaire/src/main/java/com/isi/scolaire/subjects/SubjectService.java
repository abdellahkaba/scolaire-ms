package com.isi.scolaire.subjects;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface SubjectService {

    SubjectResponse addSubject(SubjectRequest subjectRequest);
    SubjectResponse getSubjectById(Long id);
    List<SubjectResponse> getAllSubjects();
    SubjectResponse updateSubject(SubjectRequest request);
    void deleteSubjectById(Long id);
    PageResponse<SubjectResponse> listAllSubjects(int page, int size);
}
