package com.isi.scolaire.registration;


import com.isi.scolaire.administrativeAgent.AdministrativeAgent;
import com.isi.scolaire.administrativeAgent.AdministrativeAgentRepository;
import com.isi.scolaire.academieYear.AcademieYear;
import com.isi.scolaire.academieYear.AcademieYearRepository;
import com.isi.scolaire.classes.Classe;
import com.isi.scolaire.classes.ClasseRepository;
import com.isi.scolaire.program.Program;
import com.isi.scolaire.program.ProgramRepository;
import com.isi.scolaire.student.Student;
import com.isi.scolaire.student.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses =
        {
                StudentRepository.class,
                AdministrativeAgentRepository.class,
                ClasseRepository.class,
                ProgramRepository.class,
                AcademieYearRepository.class
        })
public interface RegistrationMapper {
    @Mapping(source = "studentId", target = "student", qualifiedByName = "mapStudentIdToStudent")
    @Mapping(source = "administrativeAgentId", target = "administrativeAgent", qualifiedByName = "mapAdministrativeAgentIdToAdministrativeAgent")
    @Mapping(source = "classeId", target = "classe", qualifiedByName = "mapClasseIdToClasse")
    @Mapping(source = "programId", target = "program", qualifiedByName = "mapProgramIdToProgram")
    @Mapping(source = "academieYearId", target = "academieYear", qualifiedByName = "mapAcademieYearIdToAcademieYear")
    Registration toRegistration(RegistrationRequest request);

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "administrativeAgent.id", target = "administrativeAgentId")
    @Mapping(source = "classe.id", target = "classeId")
    @Mapping(source = "program.id", target = "programId")
    @Mapping(source = "academieYear.id", target = "academieYearId")
    @Mapping(source = "student.firstName", target = "studentFistName")
    @Mapping(source = "student.lastName", target = "studentLastName")
    @Mapping(source = "student.registrationNu", target = "registrationNu")
    @Mapping(source = "administrativeAgent.firstName", target = "administrativeFirstName")
    @Mapping(source = "administrativeAgent.lastName", target = "administrativeLastName")
    @Mapping(source = "program.name", target = "programName")
    @Mapping(source = "classe.name", target = "classeName")
    @Mapping(source = "academieYear.name", target = "academieName")

    RegistrationResponse toRegistrationResponse(Registration registration);
    List<RegistrationResponse> toRegistrationResponseList(List<Registration> registrations);

    @Named("mapStudentIdToStudent")
    static Student mapStudentIdToStudent(Long studentId) {
        if (studentId == null) {
            return null;
        }
        Student student = new Student();
        student.setId(studentId);
        return student;
    }

    @Named("mapAdministrativeAgentIdToAdministrativeAgent")
    static AdministrativeAgent mapAdministrativeAgentIdToAdministrativeAgent(Long administrativeAgentId) {
        if (administrativeAgentId == null) {
            return null;
        }
        AdministrativeAgent administrativeAgent = new AdministrativeAgent();
        administrativeAgent.setId(administrativeAgentId);
        return administrativeAgent;
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

    @Named("mapProgramIdToProgram")
    static Program mapProgramIdToProgram(Long programId) {
        if (programId == null) {
            return null;
        }
        Program program = new Program();
        program.setId(programId);
        return program;
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
