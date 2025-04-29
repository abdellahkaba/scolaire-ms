package com.isi.scolaire.registration;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationResponse {

    private Long id;
    private LocalDate date;
    private String description;
    private boolean archive;
    private Long studentId;
    private Long administrativeAgentId;
    private Long classeId;
    private Long programId;
    private Long academieYearId;
    private String studentFistName;
    private String studentLastName;
    private String registrationNu;
    private String administrativeFirstName;
    private String administrativeLastName;
    private String classeName;
    private String programName;
    private String academieName;
}
