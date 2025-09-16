package com.beneficiaire.controller;

import com.beneficiaire.model.Beneficiaire;
import com.beneficiaire.model.Beneficiaire.TypeBeneficiaire;
import com.beneficiaire.model.Entreprise;
import com.beneficiaire.model.PersonnePhysique;
import com.beneficiaire.service.GestionBeneficiaireService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
public class BeneficiaireController {

    private final GestionBeneficiaireService service;

    public BeneficiaireController(GestionBeneficiaireService service) {
        this.service = service;
    }

    // POST /api/entreprises
    @PostMapping("/entreprises")
    public ResponseEntity<Entreprise> creerEntreprise(@RequestBody Map<String, String> body) {
        String nom = body.get("nom");
        if (nom == null || nom.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        Entreprise e = service.ajouterEntreprise(nom);
        return ResponseEntity.status(HttpStatus.CREATED).body(e);
    }

    // POST /api/personnes
    @PostMapping("/personnes")
    public ResponseEntity<PersonnePhysique> creerPersonnePhysique(@RequestBody Map<String, String> body) {
        String nom = body.get("nom");
        String prenom = body.get("prenom");
        if (nom == null || nom.isBlank() || prenom == null || prenom.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        PersonnePhysique p = service.ajouterPersonnePhysique(nom, prenom);
        return ResponseEntity.status(HttpStatus.CREATED).body(p);
    }

    // POST /api/beneficiaires
    @PostMapping("/beneficiaires")
    public ResponseEntity<String> ajouterBeneficiaire(@RequestBody Map<String, Object> body) {
        String entrepriseId = (String) body.get("entrepriseId");
        String beneficiaireId = (String) body.get("beneficiaireId");
        String typeStr = (String) body.get("type");
        Object pourcentageObj = body.get("pourcentage");

        if (entrepriseId == null || beneficiaireId == null || typeStr == null || pourcentageObj == null) {
            return ResponseEntity.badRequest().body("Données manquantes");
        }

        TypeBeneficiaire type;
        try {
            type = TypeBeneficiaire.valueOf(typeStr);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body("Type de bénéficiaire invalide");
        }

        double pourcentage;
        try {
            pourcentage = ((Number) pourcentageObj).doubleValue();
        } catch (ClassCastException ex) {
            return ResponseEntity.badRequest().body("Pourcentage invalide");
        }

        try {
            boolean ok = service.ajouterBeneficiaire(entrepriseId, beneficiaireId, type, pourcentage);
            if (!ok) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entreprise non trouvée");
            }
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Bénéficiaire ajouté");
    }

    // GET /api/entreprises/{id}/beneficiaires
    @GetMapping("/entreprises/{id}/beneficiaires")
    public ResponseEntity<?> getBeneficiaires(
            @PathVariable("id") String entrepriseId,
            @RequestParam(name = "type", required = false) String typeStr,
            @RequestParam(name = "effectifs", required = false, defaultValue = "false") boolean seulementEffectifs) {

        Optional<TypeBeneficiaire> filtreType = Optional.empty();
        if (typeStr != null) {
            try {
                filtreType = Optional.of(TypeBeneficiaire.valueOf(typeStr));
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body("Paramètre type invalide");
            }
        }

        Optional<Entreprise> entOpt = service.getEntreprise(entrepriseId);
        if (entOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entreprise non trouvée");
        }

        List<Beneficiaire> list = service.getBeneficiaires(entrepriseId, filtreType, seulementEffectifs);

        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(list);
    }
}
