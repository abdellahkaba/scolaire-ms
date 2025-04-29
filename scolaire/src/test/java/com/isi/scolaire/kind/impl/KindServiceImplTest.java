package com.isi.scolaire.kind.impl;

import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.kind.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KindServiceImplTest {

    @Mock
    private KindRepository kindRepository;
    @Mock
    private KindMapper kindMapper;
    @Mock
    private MessageSource messageSource;
    @InjectMocks
    private KindServiceImpl kindService;

    @Test
    void addKindOK() {
        when(kindRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(kindMapper.toKind(any())).thenReturn(getKind());
        when(kindRepository.save(any())).thenReturn(getKind());
        when(kindMapper.toKindResponse(any())).thenReturn(getKindResponse());

        KindResponse response = kindService.addKind(getKindRequest());

        assertNotNull(response);
        assertEquals("Kind1", response.getName());
    }

    @Test
    void addKindKO() {
        when(kindRepository.findByName(anyString())).thenReturn(Optional.of(getKind()));
        when(messageSource.getMessage(eq("kind.exists"), any(), any(Locale.class)))
                .thenReturn("Kind already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> kindService.addKind(getKindRequest()));

        assertEquals("Kind already exists", exception.getMessage());
    }

    @Test
    void getKindOK() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.of(getKind()));
        when(kindMapper.toKindResponse(any())).thenReturn(getKindResponse());

        KindResponse response = kindService.getKind(1L);

        assertNotNull(response);
        assertEquals("Kind1", response.getName());
    }

    @Test
    void getKindKO() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("kind.notfound"), any(), any(Locale.class)))
                .thenReturn("Kind not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> kindService.getKind(1L));

        assertEquals("Kind not found", exception.getMessage());
    }

    @Test
    void getAllKinds() {
        when(kindRepository.findAll()).thenReturn(List.of(getKind()));
        when(kindMapper.toKindResponseList(any())).thenReturn(List.of(getKindResponse()));

        List<KindResponse> responses = kindService.getAllKinds();

        assertNotNull(responses);
        assertEquals(1, responses.size());
    }

    @Test
    void updateKindOK() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.of(getKind()));
        when(kindRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(kindRepository.save(any())).thenReturn(getKind());
        when(kindMapper.toKindResponse(any())).thenReturn(getKindResponse());

        KindResponse response = kindService.updateKind(getKindRequest());

        assertNotNull(response);
        assertEquals("Kind1", response.getName());
    }

    @Test
    void updateKindKO_NotFound() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("kind.notfound"), any(), any(Locale.class)))
                .thenReturn("Kind not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> kindService.updateKind(getKindRequest()));

        assertEquals("Kind not found", exception.getMessage());
    }

    @Test
    void updateKindKO_NameExists() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.of(getKind()));
        when(kindRepository.findByName(anyString())).thenReturn(Optional.of(getKind()));
        when(messageSource.getMessage(eq("kind.exists"), any(), any(Locale.class)))
                .thenReturn("Kind already exists");

        EntityExistsException exception = assertThrows(EntityExistsException.class, () -> kindService.updateKind(getKindRequest()));

        assertEquals("Kind already exists", exception.getMessage());
    }

    @Test
    void deleteKindByIdOK() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.of(getKind()));

        kindService.deleteKindById(1L);

        verify(kindRepository, times(1)).delete(any());
    }

    @Test
    void deleteKindByIdKO() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("kind.notfound"), any(), any(Locale.class)))
                .thenReturn("Kind not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> kindService.deleteKindById(1L));

        assertEquals("Kind not found", exception.getMessage());
    }

    private KindRequest getKindRequest() {
        KindRequest request = new KindRequest();
        request.setId(1L);
        request.setName("Kind1");
        request.setDescription("Description1");
        request.setArchive(false);
        return request;
    }

    private Kind getKind() {
        Kind kind = new Kind();
        kind.setId(1L);
        kind.setName("Kind1");
        kind.setDescription("Description1");
        kind.setArchive(false);
        return kind;
    }

    private KindResponse getKindResponse() {
        return new KindResponse(1L, "Kind1", "Description1", false);
    }
}
