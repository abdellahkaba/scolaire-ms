package com.isi.scolaire.kind.impl;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.kind.*;
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
public class KindServiceImpl implements KindService {
    private final KindRepository kindRepository;
    private final KindMapper kindMapper;
    private final MessageSource messageSource;
    @Override
    public KindResponse addKind(KindRequest request) {
        if (kindRepository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("kind.exists", new Object[]{request.getName()}, Locale.getDefault()));
        }
        Kind kind = kindMapper.toKind(request);
        var savedKind = kindRepository.save(kind);
        return kindMapper.toKindResponse(savedKind);
    }

    @Override
    public KindResponse getKind(Long id) {
        return kindRepository.findById(id)
                .map(kindMapper::toKindResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("kind.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<KindResponse> getAllKinds() {
        List<Kind> kinds = kindRepository.findAll();
        return kindMapper.toKindResponseList(kinds);
    }

    @Override
    public KindResponse updateKind(KindRequest request) {
        var kind = kindRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("kind.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        if (kindRepository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("kind.exists", new Object[]{request.getName()}, Locale.getDefault()));
        }
        kind.setName(request.getName());
        kind.setDescription(request.getDescription());
        kind.setArchive(request.isArchive());
        var updatedKind = kindRepository.save(kind);
        return kindMapper.toKindResponse(updatedKind);
    }
    @Override
    public void deleteKindById(Long id) {
        Kind kind = kindRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("kind.notfound", new Object[]{id}, Locale.getDefault())));
        kindRepository.delete(kind);

    }

    @Override
    public PageResponse<KindResponse> listAllKinds(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Kind> kinds = kindRepository.findAll(pageable);
        List<KindResponse> responses = kinds.stream()
                .map(kindMapper::toKindResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                kinds.getNumber(),
                kinds.getSize(),
                kinds.getTotalElements(),
                kinds.getTotalPages(),
                kinds.isFirst(),
                kinds.isLast()
        );
    }
}
