package com.isi.scolaire.courses;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    private Long id;
    @NotBlank(message = "Le nom du cours est requis")
    private String name;
    private String description;
    @NotNull(message = "Id de la classe est requise")
    private Long classeId;
    @NotNull(message = "Id de subject est requis")
    private Long subjectId;
    private Long halfYearlyId;
    private Long academieYearId;
    private boolean archive;
}
