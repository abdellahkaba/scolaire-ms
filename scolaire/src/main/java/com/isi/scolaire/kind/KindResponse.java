package com.isi.scolaire.kind;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KindResponse {
    private Long id;
    private String name;
    private String description;
    private boolean archive;
}
