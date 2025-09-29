package com.beneficiaire.service;

import com.beneficiaire.dto.CreatePersonneDTO;
import com.beneficiaire.entity.PersonnePhysique;
import com.beneficiaire.repository.PersonneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonneServiceTest {

    @Mock
    private PersonneRepository personneRepository;

    @InjectMocks
    private PersonneService personneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPersonne_shouldReturnDTO() {
        CreatePersonneDTO dto = CreatePersonneDTO.builder()
                .nom("Dupont")
                .prenom("Jean")
                .dateNaissance(LocalDate.of(1980, 5, 15))
                .adresse("Paris")
                .build();

        PersonnePhysique saved = PersonnePhysique.builder()
                .id(1L)
                .nom(dto.getNom())
                .prenom(dto.getPrenom())
                .dateNaissance(dto.getDateNaissance())
                .adresse(dto.getAdresse())
                .build();

        when(personneRepository.save(any(PersonnePhysique.class))).thenReturn(saved);

        var result = personneService.createPersonne(dto);
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNom()).isEqualTo("Dupont");
    }

    @Test
    void getPersonneById_notFound_shouldThrow() {
        when(personneRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> personneService.getPersonneById(99L))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
