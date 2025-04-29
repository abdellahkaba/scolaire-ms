package com.isi.scolaire.sessions;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionRequest {

    private Long id;
    @NotBlank(message = "Donne le nom de sector")
    private String name;
    @NotNull(message = "Donne la de debut de la session")
    private LocalDateTime bigInDate;
    private LocalDateTime endDate;
    private String description;
    private Long teacherId;
    private Long courseId;
    private boolean archive;
}
