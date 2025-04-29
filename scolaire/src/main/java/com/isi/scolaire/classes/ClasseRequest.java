package com.isi.scolaire.classes;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClasseRequest {

    private Long id;
    @NotBlank(message = "Le name est requis")
    private String name;
    private String description;
    @NotNull(message = "L'Id Sector est requis")
    private Long sectorId;
    private boolean archive;
}
