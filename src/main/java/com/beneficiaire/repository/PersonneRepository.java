package com.beneficiaire.repository;

import com.bpifrance.beneficiaire.entity.PersonnePhysique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneRepository extends JpaRepository<PersonnePhysique, Long> {
    // méthodes personnalisées si besoin
}
