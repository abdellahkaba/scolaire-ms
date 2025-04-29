package com.isi.scolaire.halfYearly;


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
@RequestMapping("halfYearly")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "HalfYearly")
public class HalfYearlyController {
    private HalfYearlyService halfYearlyService;

    @PostMapping
    public ResponseEntity<HalfYearlyResponse> newHalfYearly(
            @Valid @RequestBody HalfYearlyRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(halfYearlyService.newHalfYearly(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HalfYearlyResponse> getHalfYearlyById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(halfYearlyService.getHalfYearlyById(id));
    }

    @GetMapping
    public ResponseEntity<List<HalfYearlyResponse>> getAllHalfYearly(){
        return ResponseEntity.status(HttpStatus.OK).body(halfYearlyService.getAllHalfYearly());
    }

    @PutMapping
    public ResponseEntity<HalfYearlyResponse> updateHalfYearly(
            @Valid @RequestBody HalfYearlyRequest request) {
        HalfYearlyResponse updateHalfYearly = halfYearlyService.updateHalfYearly(request);
        return ResponseEntity.ok(updateHalfYearly);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteHalfYearlyById(@PathVariable("id") Long id) {
        halfYearlyService.deleteHalfYearlyById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<HalfYearlyResponse>> listAllHalfYearly(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(halfYearlyService.listAllHalfYearly(page, size));
    }
}
