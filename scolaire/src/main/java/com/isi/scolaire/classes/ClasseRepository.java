package com.isi.scolaire.classes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClasseRepository extends JpaRepository<Classe,Long> {
    Optional<Classe> findByNameAndSectorId(String name, Long sectorId);
}
