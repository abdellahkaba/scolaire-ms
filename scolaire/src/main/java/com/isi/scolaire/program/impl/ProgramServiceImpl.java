package com.isi.scolaire.program.impl;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.exception.EntityExistsException;
import com.isi.scolaire.exception.EntityNotFoundException;
import com.isi.scolaire.kind.KindRepository;
import com.isi.scolaire.program.*;
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
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final ProgramMapper programMapper;
    private final KindRepository kindRepository;
    private final MessageSource messageSource;

    @Override
    public ProgramResponse addProgram(ProgramRequest request) {
        var kind = kindRepository.findById(request.getKindId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("kind.notfound", new Object[]{request.getKindId()}, Locale.getDefault())));
        if (programRepository.findByNameAndKindId(request.getName(), request.getKindId()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("program.kind.exists",
                    new Object[]{request.getName()}, Locale.getDefault()));
        }
        Program program = programMapper.toProgram(request);
        program.setKind(kind);
        var savedProgram = programRepository.save(program);
        return programMapper.toProgramResponse(savedProgram);
    }

    @Override
    public ProgramResponse getProgram(Long id) {
        return programRepository.findById(id)
                .map(programMapper::toProgramResponse)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("program.notfound", new Object[]{id}, Locale.getDefault())));
    }

    @Override
    public List<ProgramResponse> getAllPrograms() {
        List<Program> programs = programRepository.findAll();
        return programMapper.toProgramResponseList(programs);
    }

    @Override
    public ProgramResponse updateProgram(ProgramRequest request) {
        var kind = kindRepository.findById(request.getKindId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("kind.notfound", new Object[]{request.getKindId()}, Locale.getDefault())));
        if (programRepository.findByNameAndKindId(request.getName(), request.getKindId()).isPresent()) {
            throw new EntityExistsException(messageSource.getMessage("program.kind.exists",
                    new Object[]{request.getName()}, Locale.getDefault()));
        }

        var program = programRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("program.notfound", new Object[]{request.getId()}, Locale.getDefault())));
        program.setName(request.getName());
        program.setDescription(request.getDescription());
        program.setKind(kind);
        var updatedProgram = programRepository.save(program);
        return programMapper.toProgramResponse(updatedProgram);
    }

    @Override
    public void deleteProgramById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("program.notfound", new Object[]{id}, Locale.getDefault())));
        programRepository.delete(program);

    }

    @Override
    public PageResponse<ProgramResponse> listAllPrograms(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Program> programs = programRepository.findAll(pageable);
        List<ProgramResponse> responses = programs.stream()
                .map(programMapper::toProgramResponse)
                .collect(Collectors.toList());
        return new PageResponse<>(
                responses,
                programs.getNumber(),
                programs.getSize(),
                programs.getTotalElements(),
                programs.getTotalPages(),
                programs.isFirst(),
                programs.isLast()
        );
    }
}
