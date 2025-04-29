package com.isi.scolaire.halfYearly;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class HalfYearlyResponse {

    private Long id;
    private String name;
    private String description;
    private boolean archive;
}
