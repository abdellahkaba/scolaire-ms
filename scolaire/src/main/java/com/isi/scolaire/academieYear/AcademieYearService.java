package com.isi.scolaire.academieYear;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.teacher.TeacherResponse;

import java.util.List;

public interface AcademieYearService {
    AcademieYearResponse addAcademieYear(AcademieYearRequest request);
    AcademieYearResponse getAcademieYearById(Long id);
    List<AcademieYearResponse> getAllAcademieYears();
    AcademieYearResponse updateAcademieYear(AcademieYearRequest request);
    void deleteAcademieYearById(Long id);
    PageResponse<AcademieYearResponse> listAllAcademieYears(int page, int size);
}
