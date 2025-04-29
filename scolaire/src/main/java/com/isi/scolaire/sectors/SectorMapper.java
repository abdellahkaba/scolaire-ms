package com.isi.scolaire.sectors;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SectorMapper {

    Sector toSector(SectorRequest request);
    SectorResponse toSectorResponse(Sector sector);
    List<SectorResponse> toSectorResponseList(List<Sector> sectors);
}
