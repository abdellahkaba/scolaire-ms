package com.isi.scolaire.administrativeAgent.impl;

import com.isi.scolaire.administrativeAgent.*;
import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.registration.Registration;
import com.isi.scolaire.registration.RegistrationResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Setter
@Getter

public class AdministrativeAgentServiceImpl implements AdministrativeAgentService {
    private final AdministrativeAgentRepository repository;
    private final AdministrativeAgentMapper administrativeAgentMapper;
    private final MessageSource messageSource;
    private final AdministrativeAgentRepository administrativeAgentRepository;

    @Override
    public AdministrativeAgentResponse addAgent(AdministrativeAgentRequest request) {
        if (repository.findByEmailPerso(request.getEmailPerso()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("emailPerso.exists", new
                    Object[]{request.getEmailPerso()}, Locale.getDefault()));
        }
        if (repository.findByEmailPro(request.getEmailPro()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("emailPro.exists", new
                    Object[]{request.getEmailPerso()}, Locale.getDefault()));
        }
        if (repository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("phoneNumber.exists", new
                    Object[]{request.getPhoneNumber()}, Locale.getDefault()));
        }
        AdministrativeAgent administrativeAgent = administrativeAgentMapper.toAdministrativeAgent(request);
        var saveAdministrativeAgent = repository.save(administrativeAgent);
        return administrativeAgentMapper.toAdministrativeAgentResponse(saveAdministrativeAgent);
    }

    @Override
    public AdministrativeAgentResponse getAgent(Long id) {
        return repository.findById(id)
                .map(administrativeAgentMapper::toAdministrativeAgentResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("administrativeAgent.notfound",
                        new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<AdministrativeAgentResponse> getAllAgents() {
        List<AdministrativeAgent> agents = repository.findAll();
        return administrativeAgentMapper.toAdministrativeAgentResponseList(agents);
    }

    @Override
    public AdministrativeAgentResponse updateAgent(AdministrativeAgentRequest request) {
        repository.findByEmailPerso(request.getEmailPerso())
                .ifPresent(existingEmail -> {
                    if (!existingEmail.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("emailPerso.exists", new Object[]{existingEmail}, Locale.getDefault() ));
                    }
                });
        repository.findByEmailPro(request.getEmailPro())
                .ifPresent(existingEmail -> {
                    if (!existingEmail.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("emailPro.exists", new Object[]{existingEmail}, Locale.getDefault() ));
                    }
                });
        repository.findByPhoneNumber(request.getPhoneNumber())
                .ifPresent(existingPhone -> {
                    if (!existingPhone.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("phoneNumber.exists", new Object[]{existingPhone}, Locale.getDefault() ));
                    }
                });
        var agent = repository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("administrativeAgent.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        agent.setFirstName(request.getFirstName());
        agent.setLastName(request.getLastName());
        agent.setEmailPerso(request.getEmailPerso());
        agent.setEmailPro(request.getEmailPro());
        agent.setPhoneNumber(request.getPhoneNumber());
        agent.setAddress(request.getAddress());
        agent.setArchive(request.isArchive());
        var updateAgent = repository.save(agent);
        return administrativeAgentMapper.toAdministrativeAgentResponse(updateAgent);

    }

    @Override
    public void deleteAgent(Long id) {
        AdministrativeAgent agent = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("administrativeAgent.notfound", new Object[]{id}, Locale.getDefault())));
        repository.delete(agent);
    }

    @Override
    public PageResponse<AdministrativeAgentResponse> listAllAdminAgents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdministrativeAgent> agents = administrativeAgentRepository.findAll(pageable);
        List<AdministrativeAgentResponse> responses = agents.stream()
                .map(administrativeAgentMapper::toAdministrativeAgentResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                agents.getNumber(),
                agents.getSize(),
                agents.getTotalElements(),
                agents.getTotalPages(),
                agents.isFirst(),
                agents.isLast()
        );
    }
}
