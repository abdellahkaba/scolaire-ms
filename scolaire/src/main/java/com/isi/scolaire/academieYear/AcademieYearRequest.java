package com.isi.scolaire.academieYear;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AcademieYearRequest {

    private Long id;
    @NotBlank(message = "L'année academie est requise")
    private String name;
    private String description;
    private boolean archive;
}
