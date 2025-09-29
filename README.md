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

## 🔧 Technologies

- Java 21
- Spring Boot 3.1.x
- Spring Data JPA
- PostgreSQL
- Lombok
- JUnit 5 & Mockito
- Maven

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

L’API expose les opérations suivantes via des endpoints REST :

| Ressource     | Méthode | Endpoint                       | Description                                              | Entrée (DTO)               | Sortie (DTO)               | Codes HTTP attendus                |
|---------------|---------|--------------------------------|----------------------------------------------------------|----------------------------|----------------------------|------------------------------------|
| Entreprise    | POST    | `/api/entreprises`             | Créer une nouvelle entreprise                            | `CreateEntrepriseDTO`      | `EntrepriseDTO`            | 201 Created, 400 Bad Request       |
|               | GET     | `/api/entreprises`             | Récupérer toutes les entreprises                         | -                          | List<`EntrepriseDTO`>      | 200 OK                            |
|               | GET     | `/api/entreprises/{id}`        | Récupérer une entreprise par son ID                      | -                          | `EntrepriseDTO`            | 200 OK, 404 Not Found              |
|               | DELETE  | `/api/entreprises/{id}`        | Supprimer une entreprise par son ID                      | -                          | -                          | 204 No Content, 404 Not Found      |
| Personne      | POST    | `/api/personnes`               | Créer une personne physique                              | `CreatePersonneDTO`        | `PersonneDTO`              | 201 Created, 400 Bad Request       |
|               | GET     | `/api/personnes`               | Récupérer toutes les personnes physiques                 | -                          | List<`PersonneDTO`>        | 200 OK                            |
| Bénéficiaire  | POST    | `/api/beneficiaires`           | Créer un nouveau bénéficiaire (personne ou entreprise)   | `CreateBeneficiaireDTO`    | `BeneficiaireDTO`          | 201 Created, 400 Bad Request       |
|               | GET     | `/api/beneficiaires`           | Récupérer tous les bénéficiaires                         | -                          | List<`BeneficiaireDTO`>    | 200 OK                            |

---

### Détails importants :

- ✅ Les données de création sont validées via Jakarta Validation (`@Valid`) côté contrôleur.
- 🔒 Les erreurs sont retournées au format JSON avec un message d’erreur explicite.
- 🧩 L’API repose sur une architecture DTO pour dissocier la couche présentation des entités JPA.
- 🧹 Les suppressions sont définitives (pas de soft delete).
- 🪵 Des logs sont générés pour les opérations critiques : création, suppression, erreurs.
- 🔀 Les bénéficiaires peuvent être soit des **personnes physiques** soit d’autres **entreprises**, mais pas les deux à la fois.

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


### 2. Personne Physique (POST `/api/personnes`)

- Créer une personne physique

curl -X POST http://localhost:8080/api/personnes \
  -H "Content-Type: application/json" \
  -d '{
        "nom": "Durand",
        "prenom": "Alice",
        "dateNaissance": "1985-04-10",
        "adresse": "25 rue Victor Hugo, Nantes"
      }'
      
- Réponse (201 Created) :
{
  "id": 1,
  "nom": "Durand",
  "prenom": "Alice",
  "dateNaissance": "1985-04-10",
  "adresse": "25 rue Victor Hugo, Nantes"
}

- Requête invalide (champ manquant) :

curl -X POST http://localhost:8080/api/personnes \
  -H "Content-Type: application/json" \
  -d '{
        "nom": "",
        "prenom": "Alice",
        "dateNaissance": "1985-04-10",
        "adresse": "25 rue Victor Hugo"
      }'
      
- Réponse (400 Bad Request) :
{
  "nom": "Le nom de la personne est obligatoire"
}

- Récupérer toutes les personnes physiques (GET /api/personnes)

curl http://localhost:8080/api/personnes

- Réponse (200 OK) :
[
  {
    "id": 1,
    "nom": "Durand",
    "prenom": "Alice",
    "dateNaissance": "1985-04-10",
    "adresse": "25 rue Victor Hugo, Nantes"
  },
  {
    "id": 2,
    "nom": "Martin",
    "prenom": "Julien",
    "dateNaissance": "1991-12-01",
    "adresse": "10 boulevard Haussmann, Paris"
  }
]

### 3. Bénéficiaire (POST `/api/beneficiaires`)

💡 Un bénéficiaire peut être une personne physique ou une entreprise.

- Ajouter un bénéficiaire personne physique

curl -X POST http://localhost:8080/api/beneficiaires \
  -H "Content-Type: application/json" \
  -d '{
        "entrepriseId": 1,
        "personneId": 2,
        "pourcentage": 33.3
      }'
      
- Réponse (201 Created) :
{
  "id": 1,
  "pourcentage": 33.3,
  "entreprise": {
    "id": 1,
    "nom": "InnovCorp",
    "siren": "123456789",
    "adresse": "15 avenue des Lumières, Paris"
  },
  "personnePhysique": {
    "id": 2,
    "nom": "Martin",
    "prenom": "Julien"
  }
}

- Ajouter un bénéficiaire entreprise

curl -X POST http://localhost:8080/api/beneficiaires \
  -H "Content-Type: application/json" \
  -d '{
        "entrepriseId": 1,
        "entrepriseBeneficiaireId": 2,
        "pourcentage": 45.0
      }'
- Réponse (201 Created) :
{
  "id": 2,
  "pourcentage": 45.0,
  "entreprise": {
    "id": 1,
    "nom": "InnovCorp",
    "siren": "123456789",
    "adresse": "15 avenue des Lumières, Paris"
  },
  "entrepriseBeneficiaire": {
    "id": 2,
    "nom": "AlphaTech",
    "siren": "987654321"
  }
}

- Requête invalide (champ manquant)

curl -X POST http://localhost:8080/api/beneficiaires \
  -H "Content-Type: application/json" \
  -d '{
        "entrepriseId": 1,
        "pourcentage": 33.3
      }'

- Réponse (400 Bad Request) :
{
  "error": "Un bénéficiaire doit être soit une personne physique, soit une entreprise."
}

- Requête invalide (pourcentage négatif)

curl -X POST http://localhost:8080/api/beneficiaires \
  -H "Content-Type: application/json" \
  -d '{
        "entrepriseId": 1,
        "personneId": 2,
        "pourcentage": -10
      }'

- Réponse (400 Bad Request) :
{
  "pourcentage": "Le pourcentage doit être positif"
}

- Récupérer tous les bénéficiaires (GET /api/beneficiaires)

curl http://localhost:8080/api/beneficiaires

- Réponse (200 OK) :
[
  {
    "id": 1,
    "pourcentage": 33.3,
    "entreprise": {
      "id": 1,
      "nom": "InnovCorp"
    },
    "personnePhysique": {
      "id": 2,
      "nom": "Martin",
      "prenom": "Julien"
    }
  },
  {
    "id": 2,
    "pourcentage": 45.0,
    "entreprise": {
      "id": 1,
      "nom": "InnovCorp"
    },
    "entrepriseBeneficiaire": {
      "id": 2,
      "nom": "AlphaTech"
    }
  }
]

---

## 🔍 À faire avant de lancer les tests ou l’application

Voici les étapes essentielles à effectuer pour que l'application fonctionne correctement et que les tests soient exécutables :

- **Configurer la base de données PostgreSQL**  
  Créer une base nommée `bpifrance` avec un utilisateur PostgreSQL disposant des droits nécessaires.

- **Vérifier le fichier `application.properties`**  
  S'assurer que l'URL de connexion, les identifiants et les propriétés JPA sont correctement renseignés.

- **Prévoir des données initiales (optionnel)**  
  Ajouter un fichier `data.sql` pour injecter automatiquement des entreprises, personnes et bénéficiaires au démarrage.

- **Activer une base de test (H2)**  
  Ajouter un profil `test` avec une configuration dédiée (`application-test.properties`) pour exécuter les tests en mémoire.

- **Ajouter la dépendance H2 dans le `pom.xml`**  
  Permet d'exécuter des tests unitaires sans connexion réelle à PostgreSQL.

- **Annoter les classes de test**  
  Utiliser `@ActiveProfiles("test")` pour activer le profil de test durant l’exécution.

- **Exécuter les tests unitaires**  
  Lancer la commande `mvn test` pour valider le bon fonctionnement des services et du mapping.
