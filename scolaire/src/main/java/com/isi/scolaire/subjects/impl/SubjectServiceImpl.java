package com.isi.scolaire.subjects.impl;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.subjects.*;
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
@Setter
@Getter
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final MessageSource messageSource;
    @Override
    public SubjectResponse addSubject(SubjectRequest subjectRequest) {

        if (subjectRepository.findByName(subjectRequest.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("subject.exists", new Object[]{subjectRequest.getName()}, Locale.getDefault()));
        }
        Subject subject = subjectMapper.toSubject(subjectRequest);
        var savedSubject = subjectRepository.save(subject);
        return subjectMapper.toSubjectResponse(savedSubject);
    }

    @Override
    public SubjectResponse getSubjectById(Long id) {
        return subjectRepository.findById(id)
                .map(subjectMapper::toSubjectResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("subject.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<SubjectResponse> getAllSubjects() {
        return subjectMapper.toSubjectResponseList(subjectRepository.findAll());
    }

    @Override
    public SubjectResponse updateSubject(SubjectRequest request) {

        var subject = subjectRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("subject.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        var updatedSubject = subjectRepository.save(subject);
        subject.setName(request.getName());
        subject.setDescription(request.getDescription());
        subject.setArchive(request.isArchive());
        return subjectMapper.toSubjectResponse(updatedSubject);
    }

    @Override
    public void deleteSubjectById(Long id) {

        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("subject.notfound", new Object[]{id}, Locale.getDefault())));
        subjectRepository.delete(subject);

    }

    @Override
    public PageResponse<SubjectResponse> listAllSubjects(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Subject> subjects = subjectRepository.findAll(pageable);
        List<SubjectResponse> responses = subjects.stream()
                .map(subjectMapper::toSubjectResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                subjects.getNumber(),
                subjects.getSize(),
                subjects.getTotalElements(),
                subjects.getTotalPages(),
                subjects.isFirst(),
                subjects.isLast()
        );
    }
}
