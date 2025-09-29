package com.beneficiaire.service;

import com.beneficiaire.dto.CreateBeneficiaireDTO;
import com.beneficiaire.entity.Beneficiaire;
import com.beneficiaire.entity.Entreprise;
import com.beneficiaire.entity.PersonnePhysique;
import com.beneficiaire.repository.BeneficiaireRepository;
import com.beneficiaire.repository.EntrepriseRepository;
import com.beneficiaire.repository.PersonneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class BeneficiaireServiceTest {

    @Mock
    private BeneficiaireRepository beneficiaireRepository;

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @Mock
    private PersonneRepository personneRepository;

    @InjectMocks
    private BeneficiaireService beneficiaireService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addBeneficiaire_withPersonne_shouldSucceed() {
        Long entrepriseId = 1L;
        Long personneId = 2L;

        Entreprise entreprise = Entreprise.builder().id(entrepriseId).nom("Test SARL").siret("123").build();
        PersonnePhysique personne = PersonnePhysique.builder().id(personneId).nom("Dupont").prenom("Jean").build();

        CreateBeneficiaireDTO dto = CreateBeneficiaireDTO.builder()
                .entrepriseId(entrepriseId)
                .personneId(personneId)
                .pourcentage(BigDecimal.valueOf(33.0))
                .build();

        when(entrepriseRepository.findById(entrepriseId)).thenReturn(Optional.of(entreprise));
        when(personneRepository.findById(personneId)).thenReturn(Optional.of(personne));
        when(beneficiaireRepository.save(any(Beneficiaire.class)))
                .thenAnswer(invocation -> {
                    Beneficiaire b = invocation.getArgument(0);
                    b.setId(99L);
                    return b;
                });

        var result = beneficiaireService.addBeneficiaire(dto);

        assertThat(result.getId()).isEqualTo(99L);
        assertThat(result.getPersonneId()).isEqualTo(personneId);
        assertThat(result.getEntrepriseBeneficiaireId()).isNull();
    }

    @Test
    void addBeneficiaire_withMissingEntreprise_shouldThrow() {
        CreateBeneficiaireDTO dto = CreateBeneficiaireDTO.builder()
                .entrepriseId(100L)
                .personneId(1L)
                .pourcentage(BigDecimal.TEN)
                .build();

        when(entrepriseRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> beneficiaireService.addBeneficiaire(dto))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Entreprise introuvable");
    }

    @Test
    void addBeneficiaire_withoutPersonneOrEntreprise_shouldThrow() {
        CreateBeneficiaireDTO dto = CreateBeneficiaireDTO.builder()
                .entrepriseId(1L)
                .pourcentage(BigDecimal.TEN)
                .build();

        Entreprise entreprise = Entreprise.builder().id(1L).nom("Test SARL").build();
        when(entrepriseRepository.findById(1L)).thenReturn(Optional.of(entreprise));

        assertThatThrownBy(() -> beneficiaireService.addBeneficiaire(dto))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
