package com.isi.scolaire.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseResponse {
    private Long id;
    private String name;
    private String description;
    private Long classeId;
    private Long subjectId;
    private Long halfYearlyId;
    private Long academieYearId;
    private String classeName;
    private String subjectName;
    private String halfYearlyName;
    private String academieName;
    private boolean archive;
}
