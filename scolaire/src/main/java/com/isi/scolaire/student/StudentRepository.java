package com.isi.scolaire.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmailPerso(String emailPerso);
    Optional<Student> findByEmailPro(String emailPro);
    Optional<Student> findByRegistrationNu(String registrationNumber);
    Optional<Student> findByPhoneNumber(String phoneNumber);


}
