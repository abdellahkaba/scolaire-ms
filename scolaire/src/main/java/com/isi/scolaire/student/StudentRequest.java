package com.isi.scolaire.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {
    private Long id;
    @NotBlank(message = "Le prenom est requis")
    private String firstName;
    @NotBlank(message = "Le nom est requis")
    private String lastName;
    @NotBlank(message = "L'Email est requis")
    @Email(message = "Le Type email est requis")
    private String emailPro;
    @Email(message = "Le Type email est requis")
    private String emailPerso;
    @NotBlank(message = "Le numero de phone est requis")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Le numéro de téléphone doit être valide (ex : +1234567890)")
    private String phoneNumber;
    @NotBlank(message = "L'adresse est requis")
    private String address;
//    private String registrationNu;
//    private Long userId;
    private boolean archive;
}
