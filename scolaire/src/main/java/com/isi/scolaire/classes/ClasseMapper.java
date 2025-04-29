package com.isi.scolaire.classes;

import com.isi.scolaire.sectors.Sector;
import com.isi.scolaire.sectors.SectorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SectorRepository.class})
public interface ClasseMapper {

    @Mapping(source = "sectorId", target = "sector",qualifiedByName = "mapSectorIdToSector")
    Classe toClasse(ClasseRequest request);
    @Mapping(source = "sector.id", target = "sectorId")
    @Mapping(source = "sector.name", target = "sectorName")
    ClasseResponse toClasseResponse(Classe classe);
    List<ClasseResponse> toClasseResponseList(List<Classe> classes);

    @Named("mapSectorIdToSector")
    static Sector mapSectorIdToSector(Long sectorId) {
        if (sectorId == null) {
            return null;
        }
        Sector sector = new Sector();
        sector.setId(sectorId);
        return sector;
    }
}
