package com.isi.scolaire.subjects;

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
@RequestMapping("subjects")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Subjects")
public class SubjectController {
    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectResponse> addSubject(
            @Valid @RequestBody SubjectRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.addSubject(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectResponse> getSubjectById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.getSubjectById(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects(){
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.getAllSubjects());
    }

    @PutMapping
    public ResponseEntity<SubjectResponse> updateSubject(
            @Valid @RequestBody SubjectRequest request) {
        SubjectResponse updateSubject = subjectService.updateSubject(request);
        return ResponseEntity.ok(updateSubject);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSubjectById(@PathVariable("id") Long id) {
        subjectService.deleteSubjectById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("by-page")
    public ResponseEntity<PageResponse<SubjectResponse>> listAllSubjects(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.listAllSubjects(page, size));
    }
}
