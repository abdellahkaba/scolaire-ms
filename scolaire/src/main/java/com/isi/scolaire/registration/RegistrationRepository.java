package com.isi.scolaire.registration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Optional<Registration> findByStudentIdAndClasseIdAndProgramIdAndAcademieYearId(
            Long studentId, Long classeId, Long programId, Long academieYearId
    );
}
