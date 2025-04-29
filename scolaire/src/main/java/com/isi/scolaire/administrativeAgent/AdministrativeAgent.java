package com.isi.scolaire.administrativeAgent;


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
@Table(name = "_administrativeAgent")
public class AdministrativeAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String emailPro;
    private String emailPerso;
    private String phoneNumber;
    private String address;
    private boolean archive;

    @OneToMany(mappedBy = "administrativeAgent",cascade = CascadeType.ALL)
    private List<Registration> registrations;

//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = true)
//    private User user;
}
