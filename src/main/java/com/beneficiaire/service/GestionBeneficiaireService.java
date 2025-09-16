package com.beneficiaire.service;

import com.beneficiaire.model.Entreprise;
import com.beneficiaire.model.PersonnePhysique;
import com.beneficiaire.model.Beneficiaire;
import com.beneficiaire.model.Beneficiaire.TypeBeneficiaire;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GestionBeneficiaireService {
    private final Map<String, Entreprise> entreprises = new HashMap<>();
    private final Map<String, PersonnePhysique> personnes = new HashMap<>();

    // Ajouter entreprise
    public Entreprise ajouterEntreprise(String nom) {
        Entreprise e = new Entreprise(nom);
        entreprises.put(e.getId(), e);
        return e;
    }

    // Ajouter personne physique
    public PersonnePhysique ajouterPersonnePhysique(String nom, String prenom) {
        PersonnePhysique p = new PersonnePhysique(nom, prenom);
        personnes.put(p.getId(), p);
        return p;
    }

    // Ajouter bénéficiaire pour entreprise
    public boolean ajouterBeneficiaire(String entrepriseId, String benefId, TypeBeneficiaire type, double pourcentage) {
        Entreprise e = entreprises.get(entrepriseId);
        if (e == null) {
            return false;
        }
        // contrôle simple : pourcentage entre 0 et 100
        if (pourcentage <= 0 || pourcentage > 100) {
            throw new IllegalArgumentException("Le pourcentage doit être > 0 et ≤ 100");
        }
        Beneficiaire b = new Beneficiaire(benefId, type, pourcentage);
        e.getBeneficiaires().add(b);
        return true;
    }
}
