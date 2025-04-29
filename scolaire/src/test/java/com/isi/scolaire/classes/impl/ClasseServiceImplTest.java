package com.isi.scolaire.classes.impl;

import com.isi.scolaire.classes.*;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.sectors.Sector;
import com.isi.scolaire.sectors.SectorRepository;
import com.isi.scolaire.subjects.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClasseServiceImplTest {

    @Mock
    private ClasseRepository classeRepository;
    @Mock
    private ClasseMapper classeMapper;
    @InjectMocks
    private ClasseServiceImpl classeService;
    @Mock
    private SectorRepository sectorRepository;
    @Mock
    private SubjectRepository subjectRepository;
    @Mock
    private MessageSource messageSource;



    @Test
    void addClasseOK() {
        when(sectorRepository.findById(anyLong())).thenReturn(Optional.of(this.getSector()));
        when(classeMapper.toClasse(any())).thenReturn(this.getClasse());
        when(classeRepository.save(any())).thenReturn(this.getClasse());
        when(classeMapper.toClasseResponse(any())).thenReturn(this.getClasseResponse());
        ClasseResponse response = classeService.addClasse(this.getClasseRequest());
    }

    @Test
    void getClasseByIdOK() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.of(getClasse()));
        when(classeMapper.toClasseResponse(any())).thenReturn(getClasseResponse());

        ClasseResponse response = classeService.getClasseById(1L);
        assertEquals(1L, response.getId());
    }

    @Test
    void getClasseByIdKO() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("classe.notfound"), any(), any(Locale.class))).thenReturn("Classe not found");
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> classeService.getClasseById(1L));
        assertEquals("Classe not found", exception.getMessage());
    }

    @Test
    void getAllClasses() {
        when(classeRepository.findAll()).thenReturn(List.of(this.getClasse()));
        when(classeMapper.toClasseResponseList(any())).thenReturn(List.of(this.getClasseResponse()));

        List<ClasseResponse> responses = classeService.getAllClasses();
        assertEquals(1, responses.size());
    }

    @Test
    void deleteClasseOK() {
        Long classeId = 1L;
        Classe classe = getClasse();
        when(classeRepository.findById(classeId)).thenReturn(Optional.of(classe));
        classeService.deleteClasseById(classeId);
        verify(classeRepository, times(1)).delete(classe); // Vérification corrigée
    }

    @Test
    void deleteClasseKO() {
        when(classeRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("classe.notfound"), any(), any(Locale.class)))
                .thenReturn("Classe not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> classeService.deleteClasseById(1L));
        assertEquals("Classe not found", exception.getMessage());
    }



    private Classe getClasse() {
        Classe classe = new Classe();
        Sector sector = new Sector();
        classe.setName("Licence 1");
        classe.setDescription("Description licence 1");
        classe.setArchive(true);
        classe.setSector(sector);
        return classe;
    }

    private ClasseResponse getClasseResponse() {
        ClasseResponse response = new ClasseResponse();
        response.setId(1L);
        response.setName("Licence 1");
        response.setDescription("Description licence 1");
        response.setArchive(true);
        response.setSectorId(1L);
        return response;
    }

    private ClasseRequest getClasseRequest() {
        ClasseRequest request = new ClasseRequest();
        request.setName("Licence 1");
        request.setDescription("Description licence 1");
        request.setArchive(true);
        request.setSectorId(1L);
        return request;
    }

    private Sector getSector() {
        Sector sector = new Sector();
        sector.setName("Privé");
        sector.setDescription("Description Privé");
        sector.setArchive(false);

        return sector;
    }

}