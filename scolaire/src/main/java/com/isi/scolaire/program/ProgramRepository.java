package com.isi.scolaire.program;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    Optional<Program> findByNameAndKindId(String name, Long kindId);
}
