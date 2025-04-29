package com.isi.scolaire.registration.impl;

import com.isi.scolaire.academieYear.AcademieYear;
import com.isi.scolaire.academieYear.AcademieYearRepository;
import com.isi.scolaire.administrativeAgent.AdministrativeAgent;
import com.isi.scolaire.administrativeAgent.AdministrativeAgentRepository;
import com.isi.scolaire.classes.Classe;
import com.isi.scolaire.classes.ClasseRepository;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.program.Program;
import com.isi.scolaire.program.ProgramRepository;
import com.isi.scolaire.registration.*;
import com.isi.scolaire.student.Student;
import com.isi.scolaire.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RegistrationServiceImplTest {
    @Mock
    private RegistrationRepository registrationRepository;
    @Mock
    private RegistrationMapper registrationMapper;
    @InjectMocks
    private RegistrationServiceImpl registrationService;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private AdministrativeAgentRepository adminRepository;
    @Mock
    private ClasseRepository classeRepository;
    @Mock
    private ProgramRepository programRepository;
    @Mock
    private AcademieYearRepository academieYearRepository;
    @Mock
    private MessageSource messageSource;

    private Classe getClasse(){
        Classe classe = new Classe();
        classe.setId(1L);
        return classe;
    }
    private Student getStudent(){
        Student student = new Student();
        student.setId(1L);
        return student;
    }
    private Program getProgram(){
        Program program = new Program();
        program.setId(1L);
        return program;
    }
    private AdministrativeAgent getAdmin(){
        AdministrativeAgent admin = new AdministrativeAgent();
        admin.setId(1L);
        return admin;
    }
    private AcademieYear getAcademieYear(){
        AcademieYear academieYear = new AcademieYear();
        academieYear.setId(1L);
        return academieYear;
    }

    private Registration getRegistration(){
        Registration registration = new Registration();
        Student student = new Student();
        Program program = new Program();
        Classe classe = new Classe();
        AdministrativeAgent admin = new AdministrativeAgent();
        AcademieYear academieYear = new AcademieYear();
        registration.setId(1L);
        registration.setStudent(student);
        registration.setClasse(classe);
        registration.setProgram(program);
        registration.setAcademieYear(academieYear);
        registration.setAdministrativeAgent(admin);
        return registration;
    }
    private RegistrationResponse getRegistrationResponse(){
        RegistrationResponse response = new RegistrationResponse();
        response.setId(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        response.setDate(LocalDate.parse("2024/09/09", formatter));
        response.setDescription("Inscription Etudiant");
        response.setArchive(false);
        response.setStudentId(1L);
        response.setClasseId(1L);
        response.setProgramId(1L);
        response.setAcademieYearId(1L);
        response.setAdministrativeAgentId(1L);
        return response;
    }
    private RegistrationRequest getRegistrationRequest(){
        RegistrationRequest request = new RegistrationRequest();
        request.setId(1L);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        request.setDate(LocalDate.parse("2024/09/09", formatter));
        request.setDescription("Inscription Etudiant");
        request.setArchive(false);
        request.setStudentId(1L);
        request.setAdministrativeAgentId(1L);
        request.setClasseId(1L);
        request.setProgramId(1L);
        request.setAcademieYearId(1L);
        return request;
    }

    @Test
    void addRegistrationOK(){
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudent()));
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(academieYearRepository.findById(anyLong())).thenReturn(Optional.of(getAcademieYear()));
        when(adminRepository.findById(anyLong())).thenReturn(Optional.of(getAdmin()));
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(getProgram()));
        when(registrationMapper.toRegistration(any())).thenReturn(this.getRegistration());
        when(registrationRepository.save(any())).thenReturn(this.getRegistration());
        when(registrationMapper.toRegistrationResponse(any())).thenReturn(this.getRegistrationResponse());

        RegistrationResponse response = registrationService.addRegistration(getRegistrationRequest());
        assertEquals("Inscription Etudiant", response.getDescription());
    }
    @Test
    void addRegistrationKO_StudentNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("student.notfound"), any(), any(Locale.class)))
                .thenReturn("The Student with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.addRegistration(this.getRegistrationRequest()));
        assertEquals("The Student with ID 1 not found", exception.getMessage());
    }
    @Test
    void addProgramKO_ClasseNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudent()));
        when(classeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("classe.notfound"), any(), any(Locale.class)))
                .thenReturn("The Classe with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.addRegistration(this.getRegistrationRequest()));
        assertEquals("The Classe with ID 1 not found", exception.getMessage());
    }

    @Test
    void addRegistrationKO_ProgramNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudent()));
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(programRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("program.notfound"), any(), any(Locale.class)))
                .thenReturn("The Program with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.addRegistration(this.getRegistrationRequest()));
        assertEquals("The Program with ID 1 not found", exception.getMessage());
    }

    @Test
    void addRegistrationKO_AcademieYearNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudent()));
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(getProgram()));
        when(academieYearRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("academieYear.notfound"), any(), any(Locale.class)))
                .thenReturn("The AcademieYear with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.addRegistration(this.getRegistrationRequest()));
        assertEquals("The AcademieYear with ID 1 not found", exception.getMessage());
    }

    @Test
    void addRegistrationKO_AdminNotFound() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(getStudent()));
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(getProgram()));
        when(academieYearRepository.findById(anyLong())).thenReturn(Optional.of(getAcademieYear()));
        when(adminRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("administrativeAgent.notfound"), any(), any(Locale.class)))
                .thenReturn("The Admin with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> registrationService.addRegistration(this.getRegistrationRequest()));
        assertEquals("The Admin with ID 1 not found", exception.getMessage());
    }



    @Test
    void getAllRegistrationsOK(){
        when(registrationRepository.findAll()).thenReturn(List.of(this.getRegistration()));
        when(registrationMapper.toRegistrationResponseList(any())).thenReturn(List.of(this.getRegistrationResponse()));

        List<RegistrationResponse> responses = registrationService.getAllRegistrations();
        assertEquals(1, responses.size());
    }

    @Test
    void deleteRegistrationOK() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.of(getRegistration()));
        registrationService.deleteRegistrationById(1L);

        verify(registrationRepository, times(1)).delete(any());
    }

    @Test
    void deleteRegistrationKO() {
        when(registrationRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("registration.notfound"), any(), any(Locale.class)))
                .thenReturn("Registration not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> registrationService.deleteRegistrationById(1L));
        assertEquals("Registration not found", exception.getMessage());
    }
}