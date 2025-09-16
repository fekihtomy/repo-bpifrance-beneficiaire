package com.beneficiaire.model;

public class Beneficiaire {
    public enum TypeBeneficiaire { PERSONNE_PHYSIQUE, ENTREPRISE }
    
    private String beneficiaireId;
    private TypeBeneficiaire type;
    private double pourcentage;

    public Beneficiaire(String beneficiaireId, TypeBeneficiaire type, double pourcentage) {
        this.beneficiaireId = beneficiaireId;
        this.type = type;
        this.pourcentage = pourcentage;
    }

    public String getBeneficiaireId() { return beneficiaireId; }
    public void setBeneficiaireId(String beneficiaireId) { this.beneficiaireId = beneficiaireId; }
    public TypeBeneficiaire getType() { return type; }
    public void setType(TypeBeneficiaire type) { this.type = type; }
    public double getPourcentage() { return pourcentage; }
    public void setPourcentage(double pourcentage) { this.pourcentage = pourcentage; }
}
