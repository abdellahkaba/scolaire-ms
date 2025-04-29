package com.isi.scolaire.classes;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface ClasseService {

    ClasseResponse addClasse(ClasseRequest request);
    ClasseResponse getClasseById(Long id);
    List<ClasseResponse> getAllClasses();
    ClasseResponse updateClasse(ClasseRequest request);
    void deleteClasseById(Long id);
    ClasseResponse addSubjectToClasse(Long classeId, Long subjectId);
    PageResponse<ClasseResponse> listAllClasses(int page, int size);

}
