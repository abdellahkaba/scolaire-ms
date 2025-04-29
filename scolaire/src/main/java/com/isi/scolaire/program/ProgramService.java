package com.isi.scolaire.program;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface ProgramService {
    ProgramResponse addProgram(ProgramRequest request);
    ProgramResponse getProgram(Long id);
    List<ProgramResponse> getAllPrograms();
    ProgramResponse updateProgram(ProgramRequest request);
    void deleteProgramById(Long id);
    PageResponse<ProgramResponse> listAllPrograms(int page, int size);
}
