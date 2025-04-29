package com.isi.scolaire.user;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emailPro;
    private String token;

//    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
//    private Student student;
//
//    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
//    private Teacher teacher;
//
//    @OneToOne(mappedBy = "users", cascade = CascadeType.ALL)
//    private AdministrativeAgent administrativeAgent;

}
