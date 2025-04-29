package com.isi.scolaire.classes;


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
@RequestMapping("classes")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Classes")
public class ClasseController {
    private final ClasseService classeService;

    @PostMapping
    public ResponseEntity<ClasseResponse> addClasse(
            @Valid @RequestBody ClasseRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(classeService.addClasse(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseResponse> getClasseById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(classeService.getClasseById(id));
    }

    @GetMapping
    public ResponseEntity<List<ClasseResponse>> getAllClasses(){
        return ResponseEntity.status(HttpStatus.OK).body(classeService.getAllClasses());
    }

    @PutMapping
    public ResponseEntity<ClasseResponse> updateClasse(
            @Valid @RequestBody ClasseRequest request) {
        ClasseResponse updateClasse = classeService.updateClasse(request);
        return ResponseEntity.ok(updateClasse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteClasseById(@PathVariable("id") Long id) {
        classeService.deleteClasseById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{classeId}/assign-subject/{subjectId}")
    public ResponseEntity<ClasseResponse> assignSubjectToClasse(
            @PathVariable Long classeId, @PathVariable Long subjectId
    ){
        ClasseResponse response = classeService.addSubjectToClasse(classeId, subjectId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<ClasseResponse>> listAllClasses(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(classeService.listAllClasses(page, size));
    }

}
