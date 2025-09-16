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

    // Récupérer une entreprise par id
    public Optional<Entreprise> getEntreprise(String entrepriseId) {
        return Optional.ofNullable(entreprises.get(entrepriseId));
    }

    // Récupérer tous les bénéficiaires effectifs ( > 25%) d'une entreprise
    public List<Beneficiaire> getBeneficiaires(String entrepriseId,
                                               Optional<TypeBeneficiaire> filtreType,
                                               boolean seulementEffectifs) {
        Entreprise e = entreprises.get(entrepriseId);
        if (e == null) {
            throw new NoSuchElementException("Entreprise non trouvée");
        }
        List<Beneficiaire> list = e.getBeneficiaires();
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        // Appliquer filtres
        List<Beneficiaire> resultat = new ArrayList<>();
        for (Beneficiaire b : list) {
            if (seulementEffectifs && b.getPourcentage() <= 25.0) {
                continue;
            }
            if (filtreType.isPresent() && b.getType() != filtreType.get()) {
                continue;
            }
            resultat.add(b);
        }
        return résultat;
    }
}
