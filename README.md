# 📦 Bpifrance - Gestion des Bénéficiaires Effectifs

Ce projet est une API REST Java 21 / Spring Boot 3 permettant de gérer des **entreprises bénéficiaires effectives**.  
Il s'agit d'une version améliorée post-MVP intégrant une base de données PostgreSQL, des logs, de la validation et des tests unitaires.

---

## 🚀 Fonctionnalités

- 🔄 CRUD complet sur les entreprises
- 🗄️ Persistance avec PostgreSQL
- ✅ Validation avec Jakarta Validation
- 🧪 Tests unitaires avec JUnit + Mockito
- 📋 Gestion d’erreurs globalisée (`@ControllerAdvice`)
- 📄 Logs via SLF4J
- 🧬 DTO pour séparation modèle / transport

---

## ⚙️ Configuration

### 🎯 PostgreSQL

Assure-toi que ta base est accessible sur `localhost:5432`

---

## 🚧 Pistes d'amélioration

Voici quelques idées pour aller plus loin et enrichir le projet :

- **Sécurisation de l’API**  
  Intégrer une authentification et autorisation (ex: OAuth2, JWT) pour sécuriser les endpoints REST.

- **Documentation API interactive**  
  Générer une documentation Swagger/OpenAPI pour faciliter l’utilisation et les tests de l’API.

- **Pipeline CI/CD**  
  Automatiser les builds, tests, et déploiements via GitHub Actions, Jenkins ou GitLab CI.

- **Monitoring & alerting**  
  Intégrer des outils de monitoring (ex: Prometheus, Grafana) et de logs centralisés (ex: ELK stack).

- **Tests d’intégration**  
  Compléter les tests unitaires par des tests d’intégration avec une base en mémoire (H2) et tests API.

- **Interface utilisateur**  
  Développer une interface front-end (React, Angular, Vue.js) pour une gestion visuelle des entreprises.

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
## Commande installation & lancement API

- git clone https://github.com/fekihtomy/repo-bpifrance-beneficiaire.git
- cd repo-bpifrance-beneficiaire
- mvn clean package -DskipTests
- java -jar target/repo-bpifrance-beneficiaire-0.0.1-SNAPSHOT.jar

## Commande de tests

- cd C:\Users\fekih\repo-bpifrance-beneficiaire

### Ajout entreprises
- curl -X POST http://localhost:8080/api/entreprises -H "Content-Type: application/json" -d "{\"nom\":\"Entreprise Test 1\"}"
- retour {"id":"f70763a2-a570-45b6-a4b3-b1f6b4a8f39f","nom":"Entreprise Test 1","beneficiaires":[]}
- curl -X POST http://localhost:8080/api/entreprises -H "Content-Type: application/json" -d "{\"nom\":\"Entreprise Test 2\"}"
- retour {"id":"48817658-ec0d-42a8-99cd-d3104fc4704d","nom":"Entreprise Test 2","beneficiaires":[]}

### Ajout personne physique
- curl -X POST http://localhost:8080/api/personnes -H "Content-Type: application/json" -d "{\"nom\":\"Fekih\", \"prenom\":\"Tomy\"}"
- retour {"id":"48d0c7c7-8bdd-48aa-afd7-e3e2e75ef200","nom":"Fekih","prenom":"Tomy"}
- curl -X POST http://localhost:8080/api/personnes -H "Content-Type: application/json" -d "{\"nom\":\"Fekih\", \"prenom\":\"Joud\"}"
- retour {"id":"914ab397-640f-4705-aa1b-649fbe018627","nom":"Fekih","prenom":"Joud"}
- curl -X POST http://localhost:8080/api/personnes -H "Content-Type: application/json" -d "{\"nom\":\"Fekih\", \"prenom\":\"Zara\"}"
- retour {"id":"6d6decf5-d4bd-47e1-87cc-ea1f4d3286fb","nom":"Fekih","prenom":"Zara"}

### Ajout beneficiaire
- curl -X POST http://localhost:8080/api/beneficiaires -H "Content-Type: application/json" -d "{\"entrepriseId\":\"f70763a2-a570-45b6-a4b3-b1f6b4a8f39f\", \"beneficiaireId\":\"48d0c7c7-8bdd-48aa-afd7-e3e2e75ef200\", \"type\":\"PERSONNE_PHYSIQUE\", \"pourcentage\":50.0}"
- retour Bénéficiaire ajouté
- curl -X POST http://localhost:8080/api/beneficiaires -H "Content-Type: application/json" -d "{\"entrepriseId\":\"48817658-ec0d-42a8-99cd-d3104fc4704d\", \"beneficiaireId\":\"914ab397-640f-4705-aa1b-649fbe018627\", \"type\":\"PERSONNE_PHYSIQUE\", \"pourcentage\":31.0}"
- retour Bénéficiaire ajouté
- curl -X POST http://localhost:8080/api/beneficiaires -H "Content-Type: application/json" -d "{\"entrepriseId\":\"f70763a2-a570-45b6-a4b3-b1f6b4a8f39f\", \"beneficiaireId\":\"6d6decf5-d4bd-47e1-87cc-ea1f4d3286fb\", \"type\":\"PERSONNE_PHYSIQUE\", \"pourcentage\":2.0}"
- retour Bénéficiaire ajouté
  
### Récupération bénéficiaire
- curl -X GET http://localhost:8080/api/entreprises/{48817658-ec0d-42a8-99cd-d3104fc4704d}/beneficiaires
- retour [{"beneficiaireId":"914ab397-640f-4705-aa1b-649fbe018627","type":"PERSONNE_PHYSIQUE","pourcentage":31.0}]
- curl -X GET http://localhost:8080/api/entreprises/{f70763a2-a570-45b6-a4b3-b1f6b4a8f39f}/beneficiaires?type=PERSONNE_PHYSIQUE
- retour [{"beneficiaireId":"48d0c7c7-8bdd-48aa-afd7-e3e2e75ef200","type":"PERSONNE_PHYSIQUE","pourcentage":50.0},{"beneficiaireId":"6d6decf5-d4bd-47e1-87cc-ea1f4d3286fb","type":"PERSONNE_PHYSIQUE","pourcentage":2.0}]

### Récupération bénéficiaire effectif
- curl -X GET http://localhost:8080/api/entreprises/{f70763a2-a570-45b6-a4b3-b1f6b4a8f39f}/beneficiaires?effectifs=true
- retour [{"beneficiaireId":"48d0c7c7-8bdd-48aa-afd7-e3e2e75ef200","type":"PERSONNE_PHYSIQUE","pourcentage":50.0}]

---

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


### Créer un beneficiaire

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
