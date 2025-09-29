package com.beneficiaire.service;

import com.beneficiaire.dto.BeneficiaireDTO;
import com.beneficiaire.dto.CreateBeneficiaireDTO;
import com.beneficiaire.entity.Beneficiaire;
import com.beneficiaire.entity.Entreprise;
import com.beneficiaire.entity.PersonnePhysique;
import com.beneficiaire.repository.BeneficiaireRepository;
import com.beneficiaire.repository.EntrepriseRepository;
import com.beneficiaire.repository.PersonneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BeneficiaireService {

    private final BeneficiaireRepository beneficiaireRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final PersonneRepository personneRepository;

    @Transactional
    public BeneficiaireDTO addBeneficiaire(CreateBeneficiaireDTO dto) {
        Entreprise ent = entrepriseRepository.findById(dto.getEntrepriseId())
                .orElseThrow(() -> new EntityNotFoundException("Entreprise introuvable avec l'ID " + dto.getEntrepriseId()));

        Beneficiaire b = new Beneficiaire();
        b.setPourcentage(dto.getPourcentage());
        b.setEntreprise(ent);

        if (dto.getPersonneId() != null) {
            PersonnePhysique p = personneRepository.findById(dto.getPersonneId())
                    .orElseThrow(() -> new EntityNotFoundException("Personne introuvable avec l'ID " + dto.getPersonneId()));
            b.setPersonne(p);
        } else if (dto.getEntrepriseBeneficiaireId() != null) {
            Entreprise entB = entrepriseRepository.findById(dto.getEntrepriseBeneficiaireId())
                    .orElseThrow(() -> new EntityNotFoundException("Entreprise bénéficiaire introuvable avec l'ID " + dto.getEntrepriseBeneficiaireId()));
            b.setEntrepriseBeneficiaire(entB);
        } else {
            throw new IllegalArgumentException("Vous devez fournir soit une personneId soit une entrepriseBeneficiaireId");
        }

        Beneficiaire saved = beneficiaireRepository.save(b);
        log.info("Bénéficiaire créé avec ID {}", saved.getId());
        return mapToDTO(saved);
    }

    @Transactional(readOnly = true)
    public List<BeneficiaireDTO> getBeneficiairesForEntreprise(Long entrepriseId) {
        // vérifie que l’entreprise existe
        entrepriseRepository.findById(entrepriseId)
                .orElseThrow(() -> new EntityNotFoundException("Entreprise introuvable avec l'ID " + entrepriseId));

        return beneficiaireRepository.findByEntrepriseId(entrepriseId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private BeneficiaireDTO mapToDTO(Beneficiaire b) {
        return BeneficiaireDTO.builder()
                .id(b.getId())
                .pourcentage(b.getPourcentage())
                .entrepriseId(b.getEntreprise().getId())
                .personneId(b.getPersonne() != null ? b.getPersonne().getId() : null)
                .entrepriseBeneficiaireId(b.getEntrepriseBeneficiaire() != null ? b.getEntrepriseBeneficiaire().getId() : null)
                .build();
    }
}
