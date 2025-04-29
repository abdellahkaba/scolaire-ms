package com.isi.scolaire.program;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramRequest {

    private Long id;
    @NotBlank(message = "Le nom de program est requis")
    private String name;
    @NotBlank(message = "Veuillez donner quelques descriptions")
    private String description;
    @NotNull(message = "Id Kind is requis!")
    private Long kindId;
    private boolean archive;
}
