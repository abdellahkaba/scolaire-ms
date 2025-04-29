package com.isi.scolaire.sectors;


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
@RequestMapping("sectors")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Sectors")
public class SectorController {

    private final SectorService sectorService;

    @PostMapping
    public ResponseEntity<SectorResponse> addKind(
            @Valid @RequestBody SectorRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(sectorService.addSector(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorResponse> getSectorById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sectorService.getSectorById(id));
    }

    @GetMapping
    public ResponseEntity<List<SectorResponse>> getAllSectors(){
        return ResponseEntity.status(HttpStatus.OK).body(sectorService.getAllSectors());
    }

    @PutMapping
    public ResponseEntity<SectorResponse> updateSector(
            @Valid @RequestBody SectorRequest request) {
        SectorResponse updateSector = sectorService.updateSector(request);
        return ResponseEntity.ok(updateSector);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSectorById(@PathVariable("id") Long id) {
        sectorService.deleteSectorById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("by-page")
    public ResponseEntity<PageResponse<SectorResponse>> listAllSectors(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(sectorService.listAllSectors(page, size));
    }
}
