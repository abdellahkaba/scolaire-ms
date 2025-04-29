package com.isi.scolaire.classes;

import com.isi.scolaire.courses.Course;
import com.isi.scolaire.registration.Registration;
import com.isi.scolaire.sectors.Sector;
import com.isi.scolaire.subjects.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_classe")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean archive;

    @OneToMany(mappedBy = "classe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "subject_classe",
            joinColumns = @JoinColumn(name = "classe_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private List<Subject> subjects;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sector_id")
    private Sector sector;

    @OneToMany(mappedBy = "classe", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Registration> registrations;
}
