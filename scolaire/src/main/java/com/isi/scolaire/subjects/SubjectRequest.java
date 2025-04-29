package com.isi.scolaire.subjects;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SubjectRequest {

    private Long id;
    @NotBlank(message = "Le Subject Name est requis")
    private String name;
    @NotBlank(message = "Veuillez donner une description")
    private String description;
    private boolean archive;
}
