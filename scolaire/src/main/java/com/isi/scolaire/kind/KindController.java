package com.isi.scolaire.kind;


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
@RequestMapping("kinds")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Kinds")
public class KindController {
    private final KindService kindService;

    @PostMapping
    public ResponseEntity<KindResponse> addKind(
            @Valid @RequestBody KindRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(kindService.addKind(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<KindResponse> getKindById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(kindService.getKind(id));
    }

    @GetMapping
    public ResponseEntity<List<KindResponse>> getAllKinds(){
        return ResponseEntity.status(HttpStatus.OK).body(kindService.getAllKinds());
    }

    @PutMapping
    public ResponseEntity<KindResponse> updateKind(
            @Valid @RequestBody KindRequest request) {
        KindResponse updateKind = kindService.updateKind(request);
        return ResponseEntity.ok(updateKind);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteKindById(@PathVariable("id") Long id) {
        kindService.deleteKindById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<KindResponse>> listAllKinds(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(kindService.listAllKinds(page, size));
    }
}
