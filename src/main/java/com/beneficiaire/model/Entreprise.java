package com.beneficiaire.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Entreprise {
    private String id;
    private String nom;
    private List<Beneficiaire> beneficiaires = new ArrayList<>();

    public Entreprise(String nom) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public List<Beneficiaire> getBeneficiaires() { return beneficiaires; }
    public void setBeneficiaires(List<Beneficiaire> beneficiaires) { this.beneficiaires = beneficiaires; }
}
