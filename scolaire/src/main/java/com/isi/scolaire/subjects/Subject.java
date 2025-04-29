package com.isi.scolaire.subjects;

import com.isi.scolaire.classes.Classe;
import com.isi.scolaire.courses.Course;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean archive;

    @OneToMany(mappedBy = "subject", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Course> courses;

    @ManyToMany(mappedBy = "subjects")
    private List<Classe> classes;
}
