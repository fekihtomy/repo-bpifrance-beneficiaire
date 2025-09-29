package com.beneficiaire.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "beneficiaires")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Beneficiaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Le pourcentage est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le pourcentage doit être > 0")
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal pourcentage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_id", nullable = false)
    private Entreprise entreprise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personne_id", nullable = true)
    private PersonnePhysique personne;  // si bénéficiaire est une personne physique

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entreprise_beneficiaire_id", nullable = true)
    private Entreprise entrepriseBeneficiaire;  // si bénéficiaire est une entreprise

    // On peut aussi ajouter un champ “type” si tu veux distinguer les deux cas

}
