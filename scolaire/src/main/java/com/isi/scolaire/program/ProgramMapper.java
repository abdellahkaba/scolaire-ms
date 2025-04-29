package com.isi.scolaire.program;


import com.isi.scolaire.kind.Kind;
import com.isi.scolaire.kind.KindRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {KindRepository.class})
public interface ProgramMapper {

    @Mapping(source = "kindId", target = "kind", qualifiedByName = "mapKindIdToKind")
    Program toProgram(ProgramRequest request);
    @Mapping(source = "kind.id", target = "kindId")
    @Mapping(source = "kind.name", target = "kindName")
    ProgramResponse toProgramResponse(Program program);
    List<ProgramResponse> toProgramResponseList(List<Program> programs);


    @Named("mapKindIdToKind")
    static Kind mapKindIdToKind(Long kindId) {
        if (kindId == null) {
            return null;
        }
        Kind kind = new Kind();
        kind.setId(kindId);
        return kind;
    }

}
