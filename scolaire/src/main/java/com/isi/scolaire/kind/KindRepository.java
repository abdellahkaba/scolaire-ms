package com.isi.scolaire.kind;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KindRepository extends JpaRepository<Kind, Long> {
    Optional<Kind> findByName(String name);
}
