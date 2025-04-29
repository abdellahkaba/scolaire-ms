package com.isi.scolaire.sectors;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class SectorRequest {
    private Long id;
    @NotBlank(message = "Le nom de sector est requis")
    private String name;
    @NotBlank(message = "veuillez donner une description")
    private String description;
    private boolean archive;
}
