package com.beneficiaire.controller;

import com.beneficiaire.dto.BeneficiaireDTO;
import com.beneficiaire.dto.CreateBeneficiaireDTO;
import com.beneficiaire.service.BeneficiaireService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beneficiaires")
@RequiredArgsConstructor
public class BeneficiaireController {

    private final BeneficiaireService beneficiaireService;

    @PostMapping
    public ResponseEntity<BeneficiaireDTO> addBeneficiaire(@Valid @RequestBody CreateBeneficiaireDTO dto) {
        BeneficiaireDTO created = beneficiaireService.addBeneficiaire(dto);
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping("/entreprise/{entrepriseId}")
    public ResponseEntity<List<BeneficiaireDTO>> getBeneficiairesForEntreprise(@PathVariable Long entrepriseId) {
        List<BeneficiaireDTO> list = beneficiaireService.getBeneficiairesForEntreprise(entrepriseId);
        return ResponseEntity.ok(list);
    }
}
