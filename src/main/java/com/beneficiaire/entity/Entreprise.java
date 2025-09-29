package com.bpifrance.beneficiaire.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "entreprises")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entreprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Le nom de l'entreprise est obligatoire")
    @Size(max = 255)
    @Column(nullable = false)
    private String nom;

    @NotBlank(message = "Le SIREN est obligatoire")
    @Size(min = 9, max = 9, message = "Le SIREN doit contenir exactement 9 chiffres")
    @Column(unique = true, nullable = false, length = 9)
    private String siren;

    @Size(max = 255)
    private String adresse;

    // Ajoute ici d'autres champs selon besoins

}
