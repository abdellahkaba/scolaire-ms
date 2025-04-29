package com.isi.scolaire.student;

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
@Table(name = "_student")

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String emailPro;
    private String emailPerso;
    private String phoneNumber;
    private String address;
    @Column(unique = true)
    private String registrationNu;
    private boolean archive;


    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Registration> registrations;

//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = true)
//    private User user;

    @PostPersist
    public void generateRegistrationNu() {
        if (this.registrationNu == null) { // Vérifie si ce n'est pas déjà défini
            this.registrationNu = generateRegNumber();
        }
    }

    private String generateRegNumber() {
        String firstLetter = (firstName != null && !firstName.isEmpty()) ? firstName.substring(0, 1).toUpperCase() : "X";
        String lastPart = (lastName != null && !lastName.isEmpty()) ? lastName.substring(0, Math.min(3, lastName.length())).toUpperCase() : "YYY";
        return lastPart + firstLetter + "-" + String.format("%05d", id);
    }

}
