package com.isi.scolaire.sectors.impl;

import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.sectors.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SectorServiceImplTest {


    @Mock
    private SectorRepository sectorRepository;
    @Mock
    private SectorMapper sectorMapper;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private SectorServiceImpl sectorService;


    @Test
    void addSectorOK() {
        when(sectorRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(sectorMapper.toSector(any())).thenReturn(getSector());
        when(sectorRepository.save(any())).thenReturn(getSector());
        when(sectorMapper.toSectorResponse(any())).thenReturn(getSectorResponse());

        SectorResponse response = sectorService.addSector(getSectorRequest());

        assertNotNull(response);
        assertEquals("Sector1", response.getName());
    }

    @Test
    void addSectorKO() {
        when(sectorRepository.findByName(anyString())).thenReturn(Optional.of(getSector()));
        when(messageSource.getMessage(eq("sector.exists"), any(), any(Locale.class)))
                .thenReturn("Sector already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> sectorService.addSector(getSectorRequest()));

        assertEquals("Sector already exists", exception.getMessage());
    }

    @Test
    void getSectorOK() {
        when(sectorRepository.findById(anyLong())).thenReturn(Optional.of(getSector()));
        when(sectorMapper.toSectorResponse(any())).thenReturn(getSectorResponse());

        SectorResponse response = sectorService.getSectorById(1L);

        assertNotNull(response);
        assertEquals("Sector1", response.getName());
    }

    @Test
    void getSectorKO() {
        when(sectorRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("sector.notfound"), any(), any(Locale.class)))
                .thenReturn("Sector not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sectorService.getSectorById(1L));

        assertEquals("Sector not found", exception.getMessage());
    }

    @Test
    void getAllSectors() {
        when(sectorRepository.findAll()).thenReturn(List.of(getSector()));
        when(sectorMapper.toSectorResponseList(any())).thenReturn(List.of(getSectorResponse()));

        List<SectorResponse> responses = sectorService.getAllSectors();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }


    @Test
    void deleteSectorByIdOK() {
        when(sectorRepository.findById(anyLong())).thenReturn(Optional.of(getSector()));

        sectorService.deleteSectorById(1L);

        verify(sectorRepository, times(1)).delete(any());
    }

    @Test
    void deleteSectorByIdKO() {
        when(sectorRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("sector.notfound"), any(), any(Locale.class)))
                .thenReturn("Sector not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> sectorService.deleteSectorById(1L));

        assertEquals("Sector not found", exception.getMessage());
    }

    private SectorRequest getSectorRequest() {
        SectorRequest request = new SectorRequest();
        request.setId(1L);
        request.setName("Sector1");
        request.setDescription("Description1");
        request.setArchive(false);
        return request;
    }

    private Sector getSector() {
        Sector Sector = new Sector();
        Sector.setId(1L);
        Sector.setName("Sector1");
        Sector.setDescription("Description1");
        Sector.setArchive(false);
        return Sector;
    }

    private SectorResponse getSectorResponse() {
        return new SectorResponse(1L, "Sector1", "Description1", false);
    }
}