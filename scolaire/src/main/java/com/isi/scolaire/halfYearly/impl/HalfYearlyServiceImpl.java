package com.isi.scolaire.halfYearly.impl;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.halfYearly.*;
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
public class HalfYearlyServiceImpl implements HalfYearlyService {
    private final HalfYearlyRepository halfYearlyRepository;
    private final HalfYearlyMapper halfYearlyMapper;
    private final MessageSource messageSource;

    @Override
    public HalfYearlyResponse newHalfYearly(HalfYearlyRequest request) {
        if (halfYearlyRepository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("halfYearly.exist", new Object[]{request.getName()}, Locale.getDefault()));
        }
        HalfYearly halfYearly = halfYearlyMapper.toHalfYearly(request);
        var savedHalfYearly = halfYearlyRepository.save(halfYearly);
        return halfYearlyMapper.toHalfYearlyResponse(savedHalfYearly);
    }

    @Override
    public HalfYearlyResponse getHalfYearlyById(Long id) {
        return halfYearlyRepository.findById(id)
                .map(halfYearlyMapper::toHalfYearlyResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("halfYearly.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<HalfYearlyResponse> getAllHalfYearly() {
        return halfYearlyMapper.toHalfYearlyResponsesList(halfYearlyRepository.findAll());
    }

    @Override
    public HalfYearlyResponse updateHalfYearly(HalfYearlyRequest request) {
        if (halfYearlyRepository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("halfYearly.exist", new Object[]{request.getName()}, Locale.getDefault()));
        }
        var halfYearly = halfYearlyRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("halfYearly.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        halfYearly.setName(request.getName());
        halfYearly.setDescription(request.getDescription());
        halfYearly.setArchive(request.isArchive());
        var updatedHalfYearly = halfYearlyRepository.save(halfYearly);
        return halfYearlyMapper.toHalfYearlyResponse(updatedHalfYearly);
    }

    @Override
    public void deleteHalfYearlyById(Long id) {
        HalfYearly halfYearly = halfYearlyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("halfYearly.notfound", new Object[]{id}, Locale.getDefault())));
        halfYearlyRepository.delete(halfYearly);
    }

    @Override
    public PageResponse<HalfYearlyResponse> listAllHalfYearly(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HalfYearly> halfYearlies = halfYearlyRepository.findAll(pageable);
        List<HalfYearlyResponse> responses = halfYearlies.stream()
                .map(halfYearlyMapper::toHalfYearlyResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                halfYearlies.getNumber(),
                halfYearlies.getSize(),
                halfYearlies.getTotalElements(),
                halfYearlies.getTotalPages(),
                halfYearlies.isFirst(),
                halfYearlies.isLast()
        );
    }
}
