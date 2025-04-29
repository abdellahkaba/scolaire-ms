package com.isi.scolaire.academieYear.impl;

import com.isi.scolaire.academieYear.*;
import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
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
@Setter
@Getter
@AllArgsConstructor
public class AcademieYearServiceImpl implements AcademieYearService {
    private final AcademieYearRepository academieYearRepository;
    private final AcademieYearMapper academieYearMapper;
    private final MessageSource messageSource;

    @Override
    public AcademieYearResponse addAcademieYear(AcademieYearRequest request) {
        if (academieYearRepository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("academieYear.exists", new Object[]{request.getName()}, Locale.getDefault()));
        }
        AcademieYear academieYear = academieYearMapper.toAcademieYear(request);
        var savedAcademieYear = academieYearRepository.save(academieYear);
        return academieYearMapper.toAcademieYearResponse(savedAcademieYear);

    }

    @Override
    public AcademieYearResponse getAcademieYearById(Long id) {
        return academieYearRepository.findById(id)
                .map(academieYearMapper::toAcademieYearResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("academieYear.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<AcademieYearResponse> getAllAcademieYears() {
        return academieYearMapper.toAcademieYearResponseList(academieYearRepository.findAll());
    }

    @Override
    public AcademieYearResponse updateAcademieYear(AcademieYearRequest request) {
        var academieYear = academieYearRepository.findById(request.getId())
                        .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("academieYear.notfound", new Object[]{request.getId()}, Locale.getDefault())));

        academieYearRepository.findByName(request.getName())
                .ifPresent(existingName -> {
                    if (!existingName.getId().equals(request.getId())) {
                        throw new EntityExistsException(messageSource.getMessage("academieYear.exists", new Object[]{existingName}, Locale.getDefault() ));
                    }
                });
        academieYear.setName(request.getName());
        academieYear.setDescription(request.getDescription());
        academieYear.setArchive(request.isArchive());
        var updatedAcademieYear = academieYearRepository.save(academieYear);
        return academieYearMapper.toAcademieYearResponse(updatedAcademieYear);
    }

    @Override
    public void deleteAcademieYearById(Long id) {
        AcademieYear academieYear = academieYearRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("academieYear.notfound", new Object[]{id}, Locale.getDefault())));
        academieYearRepository.delete(academieYear);
    }

    @Override
    public PageResponse<AcademieYearResponse> listAllAcademieYears(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AcademieYear> academieYears = academieYearRepository.findAll(pageable);
        List<AcademieYearResponse> responses = academieYears.stream()
                .map(academieYearMapper::toAcademieYearResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                academieYears.getNumber(),
                academieYears.getSize(),
                academieYears.getTotalElements(),
                academieYears.getTotalPages(),
                academieYears.isFirst(),
                academieYears.isLast()
        );
    }
}
