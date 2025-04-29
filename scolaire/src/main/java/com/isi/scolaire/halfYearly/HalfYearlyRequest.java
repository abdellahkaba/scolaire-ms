package com.isi.scolaire.halfYearly;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HalfYearlyRequest {
    private Long id;
    @NotBlank(message = "Le nom est requis")
    private String name;
    private String description;
    private boolean archive;
}
