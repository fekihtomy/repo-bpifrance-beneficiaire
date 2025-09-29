package com.beneficiaire.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntrepriseDTO {

    private Long id;
    private String nom;
    private String siren;
    private String adresse;
}
