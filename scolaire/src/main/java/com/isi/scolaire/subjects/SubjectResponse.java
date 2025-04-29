package com.isi.scolaire.subjects;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class SubjectResponse {
    private Long id;
    private String name;
    private String description;
    private boolean archive;
}
