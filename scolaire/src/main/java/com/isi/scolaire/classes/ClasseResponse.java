package com.isi.scolaire.classes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClasseResponse {

    private Long id;
    private String name;
    private String description;
    private Long SectorId;
    private String sectorName;
    private boolean archive;
}
