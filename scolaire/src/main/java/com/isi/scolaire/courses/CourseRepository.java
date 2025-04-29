package com.isi.scolaire.courses;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByNameAndClasseIdAndSubjectIdAndHalfYearlyIdAndAcademieYearId(
            String courseName,
            Long classeId, Long subjectId,
            Long halfYearlyId,
            Long academieYear);
}
