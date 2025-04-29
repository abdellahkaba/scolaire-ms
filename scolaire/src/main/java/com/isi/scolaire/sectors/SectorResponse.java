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
public class SectorResponse {
    private Long id;
    private String name;
    private String description;
    private boolean archive;
}
