package com.isi.scolaire.administrativeAgent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministrativeAgentRepository extends JpaRepository<AdministrativeAgent, Long> {
    Optional<AdministrativeAgent> findByEmailPerso(String emailPerso);
    Optional<AdministrativeAgent> findByEmailPro(String emailPro);
    Optional<AdministrativeAgent> findByPhoneNumber(String phoneNumber);
}
