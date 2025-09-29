package com.beneficiaire.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBeneficiaireDTO {

    @NotNull(message = "Le pourcentage est requis")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le pourcentage doit être > 0")
    private BigDecimal pourcentage;

    @NotNull(message = "L'entreprise ID est requis")
    private Long entrepriseId;

    // soit personneId soit entrepriseBeneficiaireId
    private Long personneId;
    private Long entrepriseBeneficiaireId;
}
