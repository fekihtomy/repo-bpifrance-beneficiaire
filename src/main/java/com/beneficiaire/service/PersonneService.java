package com.beneficiaire.service;

import com.beneficiaire.dto.CreatePersonneDTO;
import com.beneficiaire.dto.PersonneDTO;
import com.beneficiaire.entity.PersonnePhysique;
import com.beneficiaire.repository.PersonneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonneService {

    private final PersonneRepository personneRepository;

    public PersonneDTO createPersonne(CreatePersonneDTO dto) {
        PersonnePhysique p = PersonnePhysique.builder()
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .adresse(dto.getAdresse())
                .build();

        PersonnePhysique saved = personneRepository.save(p);
        log.info("Personne créée avec ID {}", saved.getId());
        return mapToDTO(saved);
    }

    public List<PersonneDTO> getAllPersonnes() {
        return personneRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public PersonneDTO getPersonneById(Long id) {
        PersonnePhysique p = personneRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personne introuvable avec l'ID " + id));
        return mapToDTO(p);
    }

    private PersonneDTO mapToDTO(PersonnePhysique p) {
        return PersonneDTO.builder()
                .id(p.getId())
                .nom(p.getNom())
                .prenom(p.getPrenom())
                .dateNaissance(p.getDateNaissance())
                .adresse(p.getAdresse())
                .build();
    }
}
