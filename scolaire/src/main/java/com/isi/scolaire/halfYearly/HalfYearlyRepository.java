package com.isi.scolaire.halfYearly;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HalfYearlyRepository extends JpaRepository<HalfYearly, Long> {
    Optional<HalfYearly> findByName(String name);
}
