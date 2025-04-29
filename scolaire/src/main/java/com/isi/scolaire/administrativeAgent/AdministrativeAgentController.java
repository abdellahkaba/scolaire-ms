package com.isi.scolaire.administrativeAgent;

import com.isi.scolaire.common.PageResponse;
import com.isi.scolaire.courses.CourseResponse;
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
@RequestMapping("agents")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "AdministrativeAgent")
public class AdministrativeAgentController {
    private final AdministrativeAgentService service;

    @PostMapping
    public ResponseEntity<AdministrativeAgentResponse> addAgent(
         @Valid @RequestBody AdministrativeAgentRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addAgent(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdministrativeAgentResponse> getAgent(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAgent(id));
    }

    @GetMapping
    public ResponseEntity<List<AdministrativeAgentResponse>> getAllAgents() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllAgents());
    }

    @PutMapping
    public ResponseEntity<AdministrativeAgentResponse> updateAgent(
            @Valid @RequestBody AdministrativeAgentRequest request
    ){
        AdministrativeAgentResponse updateAgent = service.updateAgent(request);
        return ResponseEntity.ok(updateAgent);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<AdministrativeAgentResponse> deleteAgent(@PathVariable("id") Long id){
        service.deleteAgent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<AdministrativeAgentResponse>> listAllAdminAgents(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(service.listAllAdminAgents(page, size));
    }
}
