package com.bpifrance.beneficiaire.service;

import com.bpifrance.beneficiaire.dto.CreateEntrepriseDTO;
import com.bpifrance.beneficiaire.entity.Entreprise;
import com.bpifrance.beneficiaire.repository.EntrepriseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class EntrepriseServiceTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @InjectMocks
    private EntrepriseService entrepriseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEntreprise_shouldCreateAndReturnEntrepriseDTO() {
        CreateEntrepriseDTO dto = CreateEntrepriseDTO.builder()
                .nom("Test SARL")
                .siren("123456789")
                .adresse("1 rue de Paris")
                .build();

        Entreprise saved = Entreprise.builder()
                .id(1L)
                .nom(dto.getNom())
                .siren(dto.getSiren())
                .adresse(dto.getAdresse())
                .build();

        when(entrepriseRepository.existsBySiren(dto.getSiren())).thenReturn(false);
        when(entrepriseRepository.save(any(Entreprise.class))).thenReturn(saved);

        var result = entrepriseService.createEntreprise(dto);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNom()).isEqualTo("Test SARL");
        assertThat(result.getSiren()).isEqualTo("123456789");
    }

    @Test
    void createEntreprise_shouldThrowIfSirenExists() {
        CreateEntrepriseDTO dto = CreateEntrepriseDTO.builder()
                .nom("Duplication SARL")
                .siren("111111111")
                .build();

        when(entrepriseRepository.existsBySiren("111111111")).thenReturn(true);

        assertThatThrownBy(() -> entrepriseService.createEntreprise(dto))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("existe déjà");
    }

    @Test
    void getEntrepriseById_shouldReturnDTO() {
        Entreprise entreprise = Entreprise.builder()
                .id(2L)
                .nom("Existant SARL")
                .siren("987654321")
                .adresse("Rue existante")
                .build();

        when(entrepriseRepository.findById(2L)).thenReturn(Optional.of(entreprise));

        var result = entrepriseService.getEntrepriseById(2L);

        assertThat(result.getNom()).isEqualTo("Existant SARL");
    }

    @Test
    void getEntrepriseById_shouldThrowIfNotFound() {
        when(entrepriseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> entrepriseService.getEntrepriseById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deleteEntreprise_shouldDeleteIfExists() {
        when(entrepriseRepository.existsById(3L)).thenReturn(true);

        entrepriseService.deleteEntreprise(3L);

        verify(entrepriseRepository, times(1)).deleteById(3L);
    }

    @Test
    void deleteEntreprise_shouldThrowIfNotFound() {
        when(entrepriseRepository.existsById(100L)).thenReturn(false);

        assertThatThrownBy(() -> entrepriseService.deleteEntreprise(100L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
