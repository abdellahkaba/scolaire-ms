package com.isi.scolaire.sectors;

import com.isi.scolaire.classes.Classe;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_sector")
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private boolean archive;

    @OneToMany(mappedBy = "sector", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Classe> classes;
}
