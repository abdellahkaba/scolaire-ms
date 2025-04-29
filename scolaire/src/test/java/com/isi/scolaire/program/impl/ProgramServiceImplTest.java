package com.isi.scolaire.program.impl;

import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.kind.Kind;
import com.isi.scolaire.kind.KindRepository;
import com.isi.scolaire.program.*;
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
class ProgramServiceImplTest {

    @Mock
    private ProgramRepository programRepository;
    @Mock
    private ProgramMapper programMapper;
    @InjectMocks
    private ProgramServiceImpl programService;
    @Mock
    private KindRepository kindRepository;
    @Mock
    private MessageSource messageSource;


    @Test
    void addProgramOK() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.of(getKind()));
        when(programMapper.toProgram(any())).thenReturn(this.getProgram());
        when(programRepository.save(any())).thenReturn(this.getProgram());
        when(programMapper.toProgramResponse(any())).thenReturn(this.getProgramResponse());

        ProgramResponse response = programService.addProgram(getProgramRequest());
        assertNotNull(response);
        assertEquals("Examen de sortie", response.getName());
    }

    @Test
    void addProgramKO_KindNotFound() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("kind.notfound"), any(), any(Locale.class)))
                .thenReturn("The Kind with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> programService.addProgram(this.getProgramRequest()));
        assertEquals("The Kind with ID 1 not found", exception.getMessage());
    }

    @Test
    void getProgramOK() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(getProgram()));
        when(programMapper.toProgramResponse(any())).thenReturn(getProgramResponse());

        ProgramResponse response = programService.getProgram(1L);
        assertNotNull(response);
        assertEquals("Examen de sortie", response.getName());
    }

    @Test
    void getProgramById_NotFound() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("program.notfound"), any(), any(Locale.class)))
            .thenReturn("The Program with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> programService.getProgram(1L) );
        assertEquals("The Program with ID 1 not found", exception.getMessage());
    }

    @Test
    void deleteProgramOK() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(getProgram()));
        programService.deleteProgramById(1L);

        verify(programRepository, times(1)).delete(any());
    }

    @Test
    void deleteProgramKO() {
        when(programRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("program.notfound"), any(), any(Locale.class)))
                .thenReturn("Program not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> programService.deleteProgramById(1L));
        assertEquals("Program not found", exception.getMessage());
    }

    @Test
    void getAllProgram() {
        when(programRepository.findAll()).thenReturn(List.of(this.getProgram()));
        when(programMapper.toProgramResponseList(any())).thenReturn(List.of(this.getProgramResponse()));

        List<ProgramResponse> responses = programService.getAllPrograms();
        assertEquals(1, responses.size());
    }

    @Test
    void updateProgramOK() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.of(getKind()));
        when(programRepository.findById(anyLong())).thenReturn(Optional.of(getProgram()));
        when(programRepository.findByNameAndKindId(anyString(), anyLong())).thenReturn(Optional.empty());
        when(programRepository.save(any())).thenReturn(getProgram());
        when(programMapper.toProgramResponse(any())).thenReturn(getProgramResponse());

        ProgramResponse response = programService.updateProgram(getProgramRequest());
        assertEquals("Examen de sortie", response.getName());
    }

    @Test
    void updateProgramKO_KindNotFound() {
        when(kindRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("kind.notfound"), any(), any(Locale.class)))
                .thenReturn("The Kind with ID 1 not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> programService.updateProgram(getProgramRequest()));
        assertEquals("The Kind with ID 1 not found", exception.getMessage());
    }

    private Program getProgram() {
        Program program = new Program();
        program.setName("Examen de sortie");
        program.setDescription("Description d'examen de sortie");
        program.setArchive(false);
        Kind kind = new Kind();
        program.setKind(kind);
        return program;
    }
    private ProgramRequest getProgramRequest() {
        ProgramRequest request = new ProgramRequest();
        request.setId(1L);
        request.setName("Examen de sortie");
        request.setDescription("Description d'examen de sortie");
        request.setArchive(false);
        request.setKindId(1L);
        return request;
    }
    private ProgramResponse getProgramResponse() {
        ProgramResponse response = new ProgramResponse();
        response.setName("Examen de sortie");
        response.setDescription("Description d'examen de sortie");
        response.setArchive(false);
        response.setKindId(1L);
        return response;
    }
    private Kind getKind() {
        Kind kind = new Kind();
        kind.setId(3L);
        return kind;
    }

}