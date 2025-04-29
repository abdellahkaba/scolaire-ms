package com.isi.scolaire.courses;


import com.isi.scolaire.academieYear.AcademieYear;
import com.isi.scolaire.classes.Classe;
import com.isi.scolaire.halfYearly.HalfYearly;
import com.isi.scolaire.sessions.Session;
import com.isi.scolaire.subjects.Subject;
import com.isi.scolaire.teacher.Teacher;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean archive;

    @ManyToMany(mappedBy = "courses")
    private List<Teacher> teachers;

    @ManyToOne
    @JoinColumn(name = "classe_id")
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Session> sessions;

    @ManyToOne
    @JoinColumn(name = "halfYearly_id",nullable = true)
    private HalfYearly halfYearly;

    @ManyToOne(optional = true)
    @JoinColumn(name = "academie_year_id")
    private AcademieYear academieYear;

}
