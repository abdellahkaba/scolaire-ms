package com.isi.scolaire.academieYear;
import com.isi.scolaire.courses.Course;
import com.isi.scolaire.registration.Registration;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_academieYear")
public class AcademieYear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean archive;

    @OneToMany(mappedBy = "academieYear", cascade = CascadeType.ALL)
    private List<Registration> registrations;

    @OneToMany(mappedBy = "academieYear", cascade = CascadeType.ALL)
    private List<Course> courses;
}
