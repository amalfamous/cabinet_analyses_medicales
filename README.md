```markdown
# Gestion d’un Cabinet d’Analyses Médicales

## Aperçu

Application de bureau pour la gestion d’un cabinet d’analyses médicales.  
Elle permet d’organiser les analyses, suivre les résultats et faciliter la communication entre les différents acteurs : patients, médecins, laborantins et administrateurs.

## Fonctionnalités

- Patients : consulter les analyses, télécharger les résultats (PDF/CSV), suivre les factures.  
- Médecins : prescrire des analyses, consulter les résultats.  
- Laborantins : gérer les analyses, enregistrer les résultats.  
- Administrateurs : gérer les utilisateurs, superviser le système.

## Technologies

- Java & JavaFX pour l’interface et la logique métier  
- MySQL pour la base de données  
- Maven pour la gestion des dépendances et le build

## Installation

1. Installer Java 11+, Maven et MySQL.  
2. Cloner le dépôt :

   ```
   git clone https://github.com/amalfamous/cabinet_analyses_medicales.git
   cd cabinet_analyses_medicales
   ```

3. Créer la base de données MySQL :

   ```
   CREATE DATABASE cabinet;
   ```

4. Configurer la connexion à la base dans le fichier `persistence.xml`.  
5. Construire le projet :

   ```
   mvn clean install
   ```

## Exécution

Lancer l’application :

```
mvn javafx:run

