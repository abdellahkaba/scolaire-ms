package com.isi.scolaire.program;

import com.isi.scolaire.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("programs")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Programs")
public class ProgramController {

    private final ProgramService programService;
    @PostMapping
    public ResponseEntity<ProgramResponse> addProgram(
            @Valid @RequestBody ProgramRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(programService.addProgram(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramResponse> getProgramById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(programService.getProgram(id));
    }

    @GetMapping
    public ResponseEntity<List<ProgramResponse>> getAllPrograms(){
        return ResponseEntity.status(HttpStatus.OK).body(programService.getAllPrograms());
    }

    @PutMapping
    public ResponseEntity<ProgramResponse> updateProgram(
            @Valid @RequestBody ProgramRequest request) {
        ProgramResponse updateProgram = programService.updateProgram(request);
        return ResponseEntity.ok(updateProgram);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProgramById(@PathVariable("id") Long id) {
        programService.deleteProgramById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("by-page")
    public ResponseEntity<PageResponse<ProgramResponse>> listAllPrograms(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(programService.listAllPrograms(page, size));
    }
}
