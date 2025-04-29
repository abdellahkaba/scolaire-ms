package com.isi.scolaire.sectors;

import com.isi.scolaire.common.PageResponse;

import java.util.List;

public interface SectorService {

    SectorResponse addSector(SectorRequest request);
    SectorResponse getSectorById(Long id);
    List<SectorResponse> getAllSectors();
    SectorResponse updateSector(SectorRequest request);
    void deleteSectorById(Long id);
    PageResponse<SectorResponse> listAllSectors(int page, int size);
}
