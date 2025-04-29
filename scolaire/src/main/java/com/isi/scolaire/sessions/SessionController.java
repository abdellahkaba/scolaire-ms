package com.isi.scolaire.sessions;


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
@RequestMapping("sessions")
@AllArgsConstructor
@Getter
@Setter
@Tag(name = "Sessions")
public class SessionController {

    private final SessionService sessionService;
    @PostMapping
    public ResponseEntity<SessionResponse> addSession(
            @Valid @RequestBody SessionRequest request
    )
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.addSession(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionResponse> getSessionById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getSessionById(id));
    }

    @GetMapping
    public ResponseEntity<List<SessionResponse>> getAllSessions(){
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.getAllSessions());
    }

    @PutMapping
    public ResponseEntity<SessionResponse> updateSession(
            @Valid @RequestBody SessionRequest request) {
        SessionResponse updateSession = sessionService.updateSession(request);
        return ResponseEntity.ok(updateSession);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSessionById(@PathVariable("id") Long id) {
        sessionService.deleteSessionById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("by-page")
    public ResponseEntity<PageResponse<SessionResponse>> listAllSessions(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "2", required = false) int size
    ){
        return ResponseEntity.status(HttpStatus.OK).body(sessionService.listAllSessions(page, size));
    }
}
