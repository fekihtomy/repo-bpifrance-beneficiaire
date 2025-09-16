package com.beneficiaire.model;

import java.util.UUID;

public class PersonnePhysique {
    private String id;
    private String nom;
    private String prenom;

    public PersonnePhysique(String nom, String prenom) {
        this.id = UUID.randomUUID().toString();
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getId() { return id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
}
