package com.isi.scolaire.teacher;


import com.isi.scolaire.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Optional<Teacher> findByEmailPerso(String emailPerso);
    Optional<Teacher> findByEmailPro(String emailPro);
    Optional<Teacher> findByPhoneNumber(String phoneNumber);
}
