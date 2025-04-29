package com.isi.scolaire.sectors.impl;


import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.sectors.*;
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

@AllArgsConstructor
@Getter
@Setter
@Service
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;
    private final MessageSource messageSource;
    @Override
    public SectorResponse addSector(SectorRequest request) {
        if (sectorRepository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("sector.exists", new Object[]{request.getName()}, Locale.getDefault()));
        }
        Sector sector = sectorMapper.toSector(request);
        var savedSector = sectorRepository.save(sector);
        return sectorMapper.toSectorResponse(savedSector);
    }

    @Override
    public SectorResponse getSectorById(Long id) {
        return sectorRepository.findById(id)
                .map(sectorMapper::toSectorResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("sector.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<SectorResponse> getAllSectors() {
        return sectorMapper.toSectorResponseList(sectorRepository.findAll());
    }

    @Override
    public SectorResponse updateSector(SectorRequest request) {

        var sector = sectorRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("sector.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        sector.setName(request.getName());
        sector.setDescription(request.getDescription());
        sector.setArchive(request.isArchive());
        var updatedSector = sectorRepository.save(sector);
        return sectorMapper.toSectorResponse(updatedSector);
    }

    @Override
    public void deleteSectorById(Long id) {
        Sector sector = sectorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("sector.notfound", new Object[]{id}, Locale.getDefault())));
        sectorRepository.delete(sector);
    }

    @Override
    public PageResponse<SectorResponse> listAllSectors(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Sector> sectors = sectorRepository.findAll(pageable);
        List<SectorResponse> responses = sectors.stream()
                .map(sectorMapper::toSectorResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                sectors.getNumber(),
                sectors.getSize(),
                sectors.getTotalElements(),
                sectors.getTotalPages(),
                sectors.isFirst(),
                sectors.isLast()
        );
    }
}
