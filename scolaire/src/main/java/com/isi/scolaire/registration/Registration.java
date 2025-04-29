package com.isi.scolaire.registration;

import java.time.LocalDate;

import com.isi.scolaire.administrativeAgent.AdministrativeAgent;
import com.isi.scolaire.academieYear.AcademieYear;
import com.isi.scolaire.classes.Classe;
import com.isi.scolaire.program.Program;
import com.isi.scolaire.student.Student;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_registration")
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private String description;
    private boolean archive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrative_agent_id", nullable = true)
    private AdministrativeAgent administrativeAgent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classe_id", nullable = true)
    private Classe classe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = true)
    private Program program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academie_year_id", nullable = false)
    private AcademieYear academieYear;

}
