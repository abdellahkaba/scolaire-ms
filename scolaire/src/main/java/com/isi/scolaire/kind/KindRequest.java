package com.isi.scolaire.kind;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KindRequest {
    private Long id;
    @NotBlank(message = "Donner le nom de Kind")
    private String name;
    @NotBlank(message = "Il faut decrire en quelque ligne ")
    private String description;
    private boolean archive;
}
