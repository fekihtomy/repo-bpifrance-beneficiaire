package com.beneficiaire.repository;

import com.beneficiaire.entity.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, Long> {
    
    Optional<Entreprise> findBySiren(String siren);

    boolean existsBySiren(String siren);
}
