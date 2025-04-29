package com.isi.scolaire.classes.impl;

import com.isi.scolaire.classes.*;
import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.sectors.SectorRepository;
import com.isi.scolaire.subjects.SubjectRepository;
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
public class ClasseServiceImpl implements ClasseService {
    private final ClasseRepository classeRepository;
    private final ClasseMapper classeMapper;
    private final SectorRepository sectorRepository;
    private final SubjectRepository subjectRepository;
    private final MessageSource messageSource;
    @Override
    public ClasseResponse addClasse(ClasseRequest request) {
        var Sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[] { request.getSectorId() }, Locale.getDefault())));
        if (classeRepository.findByNameAndSectorId(request.getName(), request.getSectorId()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("classe.sector.exists",
                    new Object[]{request.getName()}, Locale.getDefault()));
        }
        Classe classe = classeMapper.toClasse(request);
        classe.setSector(Sector);
        var savedClass = classeRepository.save(classe);
        return classeMapper.toClasseResponse(savedClass);
    }

    @Override
    public ClasseResponse getClasseById(Long id) {
        return classeRepository.findById(id)
                .map(classeMapper::toClasseResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<ClasseResponse> getAllClasses() {
        return classeMapper.toClasseResponseList(classeRepository.findAll());
    }

    @Override
    public ClasseResponse updateClasse(ClasseRequest request) {
        var sector = sectorRepository.findById(request.getSectorId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("sector.notfound", new Object[]{request.getSectorId()}, Locale.getDefault())));
        if (classeRepository.findByNameAndSectorId(request.getName(), request.getSectorId()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("classe.sector.exists",
                    new Object[]{request.getName()}, Locale.getDefault()));
        }
        var classe = classeRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        classe.setName(request.getName());
        classe.setDescription(request.getDescription());
        classe.setSector(sector);
        classe.setArchive(request.isArchive());
        var updatedClass = classeRepository.save(classe);
        return classeMapper.toClasseResponse(updatedClass);
    }

    @Override
    public void deleteClasseById(Long id) {
        Classe classe = classeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[]{id}, Locale.getDefault())));
        classeRepository.delete(classe);
    }

    @Override
    public ClasseResponse addSubjectToClasse(Long classeId, Long subjectId) {
        var classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("classe.notfound", new Object[]{classeId}, Locale.getDefault() )));
        var subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("subject.notfound", new Object[]{subjectId}, Locale.getDefault() )));
        classe.getSubjects().add(subject);
        subject.getClasses().add(classe);
        return classeMapper.toClasseResponse(classeRepository.save(classe));
    }

    @Override
    public PageResponse<ClasseResponse> listAllClasses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Classe> classes = classeRepository.findAll(pageable);
        List<ClasseResponse> responses = classes.stream()
                .map(classeMapper::toClasseResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                classes.getNumber(),
                classes.getSize(),
                classes.getTotalElements(),
                classes.getTotalPages(),
                classes.isFirst(),
                classes.isLast()
        );
    }


}
