package com.isi.scolaire.academieYear;


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
@RequestMapping("academieYears")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "AcademieYears")
public class AcademieYearController {
    private final AcademieYearService academieYearService;

    @PostMapping
    public ResponseEntity<AcademieYearResponse> addKind(
            @Valid @RequestBody AcademieYearRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(academieYearService.addAcademieYear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademieYearResponse> getAcademieYearById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(academieYearService.getAcademieYearById(id));
    }

    @GetMapping
    public ResponseEntity<List<AcademieYearResponse>> getAllAcademieYears(){
        return ResponseEntity.status(HttpStatus.OK).body(academieYearService.getAllAcademieYears());
    }

    @PutMapping
    public ResponseEntity<AcademieYearResponse> updateAcademieYear(
            @Valid @RequestBody AcademieYearRequest request) {
        AcademieYearResponse updateAcademieYear = academieYearService.updateAcademieYear(request);
        return ResponseEntity.ok(updateAcademieYear);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAcademieYearById(@PathVariable("id") Long id) {
        academieYearService.deleteAcademieYearById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("by-page")
    public ResponseEntity<PageResponse<AcademieYearResponse>> listAllAcademieYears(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(academieYearService.listAllAcademieYears(page, size));
    }
}
