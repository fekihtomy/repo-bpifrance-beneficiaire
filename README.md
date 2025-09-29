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

## 🛠️ Fonctionnalités disponibles (API REST)

L’API expose les opérations suivantes sur la ressource **Entreprise** via les endpoints REST :

| Méthode | Endpoint                 | Description                                       | Entrée (DTO)            | Sortie (DTO)            | Codes HTTP attendus           |
|---------|--------------------------|-------------------------------------------------|-------------------------|-------------------------|------------------------------|
| POST    | `/api/entreprises`       | Créer une nouvelle entreprise                    | `CreateEntrepriseDTO`   | `EntrepriseDTO`         | 201 Created, 400 Bad Request  |
| GET     | `/api/entreprises`       | Récupérer la liste complète des entreprises     | -                       | List<`EntrepriseDTO`>   | 200 OK                       |
| GET     | `/api/entreprises/{id}`  | Récupérer une entreprise par son ID              | -                       | `EntrepriseDTO`         | 200 OK, 404 Not Found        |
| DELETE  | `/api/entreprises/{id}`  | Supprimer une entreprise par son ID               | -                       | -                       | 204 No Content, 404 Not Found|

---

### Détails importants :

- Les données reçues en création (`POST`) sont validées avec Jakarta Validation (`@Valid`).
- Les erreurs de validation ou d’intégrité sont retournées avec un message clair au format JSON.
- L’API utilise des DTOs pour séparer la couche présentation des entités JPA.
- La suppression est définitive (pas de soft delete).
- Les logs suivent les actions importantes (création, suppression).


---

## 📚 Exemples d’utilisation de l’API

### 1. Entreprise (POST `/api/entreprises`)

- Créer une entreprise

curl -X POST http://localhost:8080/api/entreprises \
  -H "Content-Type: application/json" \
  -d '{
        "nom": "Tech Innov",
        "siren": "123456789",
        "adresse": "10 rue des Innovation, Paris"
      }'

- Réponse (201 Created) :
{
  "id": 1,
  "nom": "Tech Innov",
  "siren": "123456789",
  "adresse": "10 rue des Innovation, Paris"
}

- Requête invalide (champ manquant) :

curl -X POST http://localhost:8080/api/entreprises \
  -H "Content-Type: application/json" \
  -d '{
        "nom": "",
        "siren": "123456789"
      }'

- Réponse (400 Bad Request) :
{
  "nom": "Le nom de l'entreprise est obligatoire"
}

- Requête invalide (SIREN déjà existant) :

curl -X POST http://localhost:8080/api/entreprises \
  -H "Content-Type: application/json" \
  -d '{
        "nom": "Duplicata",
        "siren": "123456789",
        "adresse": "Adresse quelconque"
      }'

- Réponse (400 Bad Request) :
{
  "error": "Une entreprise avec ce SIREN existe déjà."
}

- Récupérer toutes les entreprises (GET /api/entreprises)

curl http://localhost:8080/api/entreprises

- Réponse (200 OK) :
[
  {
    "id": 1,
    "nom": "InnovCorp",
    "siren": "123456789",
    "adresse": "15 avenue des Lumières, Paris"
  },
  {
    "id": 2,
    "nom": "AlphaTech",
    "siren": "987654321",
    "adresse": "20 rue du Faubourg, Lyon"
  }
]

- Récupérer une entreprise par ID (GET /api/entreprises/{id})

curl http://localhost:8080/api/entreprises/1

Réponse (200 OK) :
{
  "id": 1,
  "nom": "InnovCorp",
  "siren": "123456789",
  "adresse": "15 avenue des Lumières, Paris"
}

- Réponse si ID inexistant (404 Not Found) :
{
  "error": "Entreprise introuvable avec l'ID 999"
}

- Supprimer une entreprise (DELETE /api/entreprises/{id})

curl -X DELETE http://localhost:8080/api/entreprises/1

- Réponse (204 No Content) :
Pas de contenu

- Réponse si ID inexistant (404 Not Found) :
{
  "error": "Impossible de supprimer : entreprise introuvable avec l'ID 999"
}

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
