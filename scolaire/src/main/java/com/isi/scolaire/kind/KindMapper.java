package com.isi.scolaire.kind;
import com.isi.scolaire.student.Student;
import com.isi.scolaire.student.StudentRequest;
import com.isi.scolaire.student.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface KindMapper {

    Kind toKind(KindRequest request);
    KindResponse toKindResponse(Kind kind);
    List<KindResponse> toKindResponseList(List<Kind> kinds);
}
