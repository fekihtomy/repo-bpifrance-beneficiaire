package com.beneficiaire.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEntrepriseDTO {

    @NotBlank(message = "Le nom est requis")
    @Size(max = 255)
    private String nom;

    @NotBlank(message = "Le SIREN est requis")
    @Size(min = 9, max = 9, message = "Le SIREN doit contenir exactement 9 chiffres")
    private String siren;

    @Size(max = 255)
    private String adresse;
}
