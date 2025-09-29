package com.beneficiaire.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiaireDTO {

    private Long id;
    private BigDecimal pourcentage;
    private Long entrepriseId;
    private Long personneId;
    private Long entrepriseBeneficiaireId;
}
