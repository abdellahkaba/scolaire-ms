package com.isi.scolaire.courses;


import com.isi.scolaire.academieYear.AcademieYear;
import com.isi.scolaire.academieYear.AcademieYearRepository;
import com.isi.scolaire.classes.Classe;
import com.isi.scolaire.classes.ClasseRepository;
import com.isi.scolaire.halfYearly.HalfYearly;
import com.isi.scolaire.halfYearly.HalfYearlyRepository;
import com.isi.scolaire.subjects.Subject;
import com.isi.scolaire.subjects.SubjectRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses =
        {
                ClasseRepository.class,
                SubjectRepository.class,
                HalfYearlyRepository.class,
                AcademieYearRepository.class
        })
public interface CourseMapper {
    @Mapping(source = "classeId", target = "classe", qualifiedByName = "mapClasseIdToClasse")
    @Mapping(source = "subjectId", target = "subject", qualifiedByName = "mapSubjectIdToSubject")
    @Mapping(source = "halfYearlyId", target = "halfYearly", qualifiedByName = "mapHalfYearlyIdToHalYearly")
    @Mapping(source = "academieYearId", target = "academieYear", qualifiedByName = "mapAcademieYearIdToAcademieYear")
    Course toCourse(CourseRequest request);

    @Mapping(source = "classe.id", target = "classeId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "halfYearly.id", target = "halfYearlyId")
    @Mapping(source = "academieYear.id", target = "academieYearId")
    @Mapping(source = "classe.name", target = "classeName")
    @Mapping(source = "subject.name", target = "subjectName")
    @Mapping(source = "halfYearly.name", target = "halfYearlyName")
    @Mapping(source = "academieYear.name", target = "academieName")
    CourseResponse toCourseResponse(Course course);
    List<CourseResponse> toCourseResponseList(List<Course> courses);

    @Named("mapSubjectIdToSubject")
    static Subject mapSubjectIdToSubject(Long subjectId) {
        if (subjectId == null) {
            return null;
        }
        Subject subject = new Subject();
        subject.setId(subjectId);
        return subject;
    }

    @Named("mapHalfYearlyIdToHalYearly")
    static HalfYearly mapHalfYearlyIdToHalYearly(Long halfYearlyId) {
        if (halfYearlyId == null) {
            return null;
        }
        HalfYearly halfYearly = new HalfYearly();
        halfYearly.setId(halfYearlyId);
        return halfYearly;
    }

    @Named("mapClasseIdToClasse")
    static Classe mapClasseIdToClasse(Long classeId) {
        if (classeId == null) {
            return null;
        }
        Classe classe = new Classe();
        classe.setId(classeId);
        return classe;
    }

    @Named("mapAcademieYearIdToAcademieYear")
    static AcademieYear mapAcademieYearIdToAcademieYear(Long academieYearId) {
        if (academieYearId == null) {
            return null;
        }
        AcademieYear academieYear = new AcademieYear();
        academieYear.setId(academieYearId);
        return academieYear;
    }
}
