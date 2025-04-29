package com.isi.scolaire.registration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequest {

    private Long id;
    @NotNull(message = "La date registration est requise")
    private LocalDate date;
    private String description;
    private boolean archive;
    @NotNull(message = "Donner l'Id d'Etudiant")
    private Long studentId;
    @NotNull(message = "Donner l'Id d'utilisateur")
    private Long administrativeAgentId;
    @NotNull(message = "Donner l'Id de la Classe")
    private Long classeId;
    @NotNull(message = "Donner l'Id de programme")
    private Long programId;
    @NotNull(message = "Donner l'Id Academie year")
    private Long academieYearId;
}
