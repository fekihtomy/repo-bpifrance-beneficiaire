package com.beneficiaire.service;

import com.beneficiaire.dto.CreateEntrepriseDTO;
import com.beneficiaire.dto.EntrepriseDTO;
import com.beneficiaire.entity.Entreprise;
import com.beneficiaire.repository.EntrepriseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    public EntrepriseDTO createEntreprise(CreateEntrepriseDTO dto) {
        if (entrepriseRepository.existsBySiren(dto.getSiren())) {
            throw new IllegalArgumentException("Une entreprise avec ce SIREN existe déjà");
        }

        Entreprise entreprise = Entreprise.builder()
                .nom(dto.getNom())
                .siren(dto.getSiren())
                .adresse(dto.getAdresse())
                .build();

        Entreprise saved = entrepriseRepository.save(entreprise);
        log.info("Entreprise créée avec ID {}", saved.getId());

        return mapToDTO(saved);
    }

    public List<EntrepriseDTO> getAllEntreprises() {
        List<Entreprise> entreprises = entrepriseRepository.findAll();
        return entreprises.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public EntrepriseDTO getEntrepriseById(Long id) {
        Entreprise entreprise = entrepriseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entreprise introuvable avec l'ID " + id));

        return mapToDTO(entreprise);
    }

    public void deleteEntreprise(Long id) {
        if (!entrepriseRepository.existsById(id)) {
            throw new EntityNotFoundException("Impossible de supprimer : entreprise introuvable avec l'ID " + id);
        }

        entrepriseRepository.deleteById(id);
        log.info("Entreprise supprimée avec ID {}", id);
    }

    private EntrepriseDTO mapToDTO(Entreprise entreprise) {
        return EntrepriseDTO.builder()
                .id(entreprise.getId())
                .nom(entreprise.getNom())
                .siren(entreprise.getSiren())
                .adresse(entreprise.getAdresse())
                .build();
    }
}
