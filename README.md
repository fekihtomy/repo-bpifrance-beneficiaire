# repo-bpifrance-beneficiaire

Service REST simple pour gérer les bénéficiaires effectifs d’entreprises.
MVP pour Bpifrance → connaître les bénéficiaires effectifs selon la définition : personne physique détenant directement ou indirectement **plus de 25 %** du capital.

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

## Exemples

### Créer une entreprise

```bash
curl -X POST http://localhost:8080/api/entreprises \
     -H "Content-Type: application/json" \
     -d '{ "nom": "Entreprise A" }'
