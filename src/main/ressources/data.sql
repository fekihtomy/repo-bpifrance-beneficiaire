-- Entreprises
INSERT INTO entreprises (nom, siren, adresse) VALUES
('AlphaTech', '123456789', '10 rue de la République, Paris'),
('BetaCorp', '987654321', '20 avenue des Champs, Lyon'),
('GammaIndustries', '112233445', '30 boulevard Haussmann, Marseille');

-- Personnes
INSERT INTO personnes (id, nom, prenom, date_naissance, adresse)
VALUES (1, 'Dupont', 'Jean', '1980-05-15', '12 rue des Lilas, Paris');

INSERT INTO personnes (id, nom, prenom, date_naissance, adresse)
VALUES (2, 'Martin', 'Claire', '1990-03-22', '5 avenue de la République, Lyon');

-- Bénéficiaires
INSERT INTO beneficiaires (id, pourcentage, entreprise_id, personne_id, entreprise_beneficiaire_id)
VALUES (1, 25.0, 1, 1, null);

INSERT INTO beneficiaires (id, pourcentage, entreprise_id, personne_id, entreprise_beneficiaire_id)
VALUES (2, 30.0, 1, 2, null);
