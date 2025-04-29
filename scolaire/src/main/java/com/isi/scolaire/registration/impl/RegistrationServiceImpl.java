package com.isi.scolaire.registration.impl;

import com.isi.scolaire.academieYear.AcademieYearRepository;
import com.isi.scolaire.administrativeAgent.AdministrativeAgentRepository;
import com.isi.scolaire.classes.ClasseRepository;
import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.program.ProgramRepository;
import com.isi.scolaire.registration.*;
import com.isi.scolaire.student.StudentRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Getter
@Setter
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final RegistrationMapper registrationMapper;
    private final StudentRepository studentRepository;
    private final AdministrativeAgentRepository administrativeAgentRepository;
    private final ClasseRepository classeRepository;
    private final ProgramRepository programRepository;
    private final AcademieYearRepository academieYearRepository;
    private final MessageSource messageSource;

    @Override
    public RegistrationResponse addRegistration(RegistrationRequest request) {

        var student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("student.notfound", new Object[]{request.getStudentId()}, Locale.getDefault())));
        var classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[]{request.getClasseId()}, Locale.getDefault())));

        var program = programRepository.findById(request.getProgramId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("program.notfound", new Object[]{request.getProgramId()}, Locale.getDefault())));
        var academieYear = academieYearRepository.findById(request.getAcademieYearId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("academieYear.notfound", new Object[]{request.getAcademieYearId()}, Locale.getDefault())));
        var administrativeAgent = administrativeAgentRepository.findById(request.getAdministrativeAgentId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("administrativeAgent.notfound", new Object[]{request.getAdministrativeAgentId()}, Locale.getDefault())));

        if (registrationRepository.findByStudentIdAndClasseIdAndProgramIdAndAcademieYearId(
                request.getStudentId(),
                request.getClasseId(),
                request.getProgramId(),
                request.getAcademieYearId()).isPresent()){
            throw new EntityExistsException(messageSource.getMessage("student.classe.program.academieYear.exists",
                    new Object[]{
                            request.getStudentId(),
                            request.getClasseId(),
                            request.getProgramId(),
                            request.getAcademieYearId()
                    },
                    Locale.getDefault()));
        }

        Registration registration = registrationMapper.toRegistration(request);
        registration.setStudent(student);
        registration.setProgram(program);
        registration.setAcademieYear(academieYear);
        registration.setClasse(classe);
        registration.setAdministrativeAgent(administrativeAgent);

        var savedRegistration = registrationRepository.save(registration);
        return registrationMapper.toRegistrationResponse(savedRegistration);
    }

    @Override
    public RegistrationResponse getRegistrationById(Long id) {
        return registrationRepository.findById(id)
                .map(registrationMapper::toRegistrationResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("registration.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<RegistrationResponse> getAllRegistrations() {
        return registrationMapper.toRegistrationResponseList(registrationRepository.findAll());
    }

    @Override
    public RegistrationResponse updateRegistration(RegistrationRequest request) {
        var classe = classeRepository.findById(request.getClasseId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[]{request.getClasseId()}, Locale.getDefault())));
        var student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("student.notfound", new Object[]{request.getStudentId()}, Locale.getDefault())));
        var program = programRepository.findById(request.getProgramId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("program.notfound", new Object[]{request.getProgramId()}, Locale.getDefault())));
        var academieYear = academieYearRepository.findById(request.getAcademieYearId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("academieYear.notfound", new Object[]{request.getAcademieYearId()}, Locale.getDefault())));
        var administrativeAgent = administrativeAgentRepository.findById(request.getAdministrativeAgentId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("administrativeAgent.notfound", new Object[]{request.getAdministrativeAgentId()}, Locale.getDefault())));

        var existingRegistration = registrationRepository.findByStudentIdAndClasseIdAndProgramIdAndAcademieYearId(
                request.getStudentId(),
                request.getClasseId(),
                request.getProgramId(),
                request.getAcademieYearId()
        );
        
        if (existingRegistration.isPresent() && !existingRegistration.get().getId().equals(request.getId())) {
            throw new EntityExistsException(messageSource.getMessage("student.classe.program.academieYear.exists",
                    new Object[]{
                            request.getStudentId(),
                            request.getClasseId(),
                            request.getProgramId(),
                            request.getAcademieYearId()
                    }, Locale.getDefault()));
        }

        var registration = registrationRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("registration.notfound", new Object[]{request.getId()}, Locale.getDefault())));

        registration.setStudent(student);
        registration.setProgram(program);
        registration.setAcademieYear(academieYear);
        registration.setClasse(classe);
        registration.setAdministrativeAgent(administrativeAgent);
        registration.setDate(request.getDate());
        registration.setArchive(request.isArchive());
        registration.setDescription(request.getDescription());

        var updateRegistration = registrationRepository.save(registration);
        return registrationMapper.toRegistrationResponse(updateRegistration);
    }

    @Override
    public void deleteRegistrationById(Long id) {
        Registration registration = registrationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("registration.notfound", new Object[]{id}, Locale.getDefault())));
        registrationRepository.delete(registration);
    }

    @Override
    public PageResponse<RegistrationResponse> listAllRegistrations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Registration> registrations = registrationRepository.findAll(pageable);
        List<RegistrationResponse> responses = registrations.stream()
                .map(registrationMapper::toRegistrationResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                registrations.getNumber(),
                registrations.getSize(),
                registrations.getTotalElements(),
                registrations.getTotalPages(),
                registrations.isFirst(),
                registrations.isLast()
        );
    }
}
