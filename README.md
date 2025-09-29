# repo-bpifrance-beneficiaire

Service REST simple pour gérer les bénéficiaires effectifs d’entreprises.
MVP pour Bpifrance → connaître les bénéficiaires effectifs selon la définition : personne physique détenant directement ou indirectement **plus de 25 %** du capital.

---

## Piste d'amélioration (Ce que j'aurais aimé améliorer dans le code)

- Ajout de tests JUnit type création ou récupération d'entreprise ou de personnes, l'ajout de bénéficiaires, etc...
- Ajout de base de données SQL pour la persistance
- Ajout de sécurité type authentification par exemple
- Ajout d'un historique

---

## Mon retour ou ce que je nʼai pas réussi à faire

- Manqué de temps pour des tests
- 2H c'est court, pour de vrai, je suis déjà a environ 4H
- Quelques difficultés avec les API REST, mon dernier developpement du type remonte à 2019 à la BNP pour un systeme d'automatisation de notices, developpement réalisé en Inde, j'étais plus ou moins le tech lead / MOE , rédaction spécification technique, participation aux reunions, review du code, tests puis livraison

---

## Stack technique

- Java 21
- Spring Boot 3.x
- Stockage des données **en mémoire** (non persistant) — les données seront perdues au redémarrage

---

## Fonctionnalités disponibles (API)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| **POST** | `/api/entreprises` | Créer une entreprise. Corps JSON attendu : `{ "nom": "Nom de l’entreprise" }` |
| **POST** | `/api/personnes` | Créer une personne physique. Corps JSON : `{ "nom": "...", "prenom": "..." }` |
| **POST** | `/api/beneficiaires` | Ajouter un bénéficiaire pour une entreprise. Corps JSON :<br>`{ "entrepriseId": "...", "beneficiaireId": "...", "type": "PERSONNE_PHYSIQUE" ou "ENTREPRISE", "pourcentage": nombre }` |
| **GET** | `/api/entreprises/{id}/beneficiaires` | Récupérer les bénéficiaires pour une entreprise donnée. Paramètres optionnels : `type` (= "PERSONNE_PHYSIQUE" ou "ENTREPRISE"), `effectifs` (= true/false, si true → seulement les bénéficiaires effectifs (>25%)) |

---

## Contrats / Codes de retour principaux

- **200 OK** : liste des bénéficiaires (avec pourcentage) renvoyée  
- **404 Not Found** : l’entreprise n’existe pas  
- **204 No Content** : entreprise existe mais aucun bénéficiaire ne correspond aux critères  

---
## Commande de test

git clone https://github.com/fekihtomy/repo-bpifrance-beneficiaire.git
cd repo-bpifrance-beneficiaire
mvn clean package -DskipTests
java -jar target/repo-bpifrance-beneficiaire-0.0.1-SNAPSHOT.jar

## Exemples

### Créer une entreprise

curl -X POST http://localhost:8080/api/entreprises \
     -H "Content-Type: application/json" \
     -d '{ "nom": "Entreprise Test" }'

retour attendu (201 Created):

{
  "id": "uuid-entreprise",
  "nom": "Entreprise Test",
  "beneficiaires": []
}


### Créer une personne physique

curl -X POST http://localhost:8080/api/personnes \
     -H "Content-Type: application/json" \
     -d '{ "nom": "Fekih", "prenom": "Tomy" }'

retour attendu (201 Created):

{
  "id": "uuid-personne",
  "nom": "Fekih",
  "prenom": "Tomy"
}


### Créer une personne physique

curl -X POST http://localhost:8080/api/beneficiaires \
     -H "Content-Type: application/json" \
     -d '{
           "entrepriseId": "uuid-entreprise",
           "beneficiaireId": "uuid-personne",
           "type": "PERSONNE_PHYSIQUE",
           "pourcentage": 27.0
         }'

retour attendu : 201 Created


### Récupérer tous les bénéficiaires pour une entreprise

GET http://localhost:8080/api/entreprises/{id}/beneficiaires

### Récupérer seulement les personnes physiques

GET http://localhost:8080/api/entreprises/{id}/beneficiaires?type=PERSONNE_PHYSIQUE

### Récupérer seulement les bénéficiaires effectifs

GET http://localhost:8080/api/entreprises/{id}/beneficiaires?effectifs=true
