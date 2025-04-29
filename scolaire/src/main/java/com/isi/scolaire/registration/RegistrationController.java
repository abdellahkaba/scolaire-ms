package com.isi.scolaire.registration;


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
@RequestMapping("registrations")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Registrations")
public class RegistrationController {
    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationResponse> addRegistration(
            @Valid @RequestBody RegistrationRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.addRegistration(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistrationResponse> getRegistrationById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.getRegistrationById(id));
    }

    @GetMapping
    public ResponseEntity<List<RegistrationResponse>> getAllRegistrations(){
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.getAllRegistrations());
    }

    @PutMapping
    public ResponseEntity<RegistrationResponse> updateRegistration(
            @Valid @RequestBody RegistrationRequest request) {
        RegistrationResponse updateRegistration = registrationService.updateRegistration(request);
        return ResponseEntity.ok(updateRegistration);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRegistrationById(@PathVariable("id") Long id) {
        registrationService.deleteRegistrationById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<RegistrationResponse>> listAllRegistrations(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "4", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.listAllRegistrations(page, size));
    }
}
