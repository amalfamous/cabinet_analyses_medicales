Gestion d’un Cabinet d’Analyses Médicales
Aperçu
Une application de bureau pour gérer un cabinet d’analyses médicales. Elle aide à organiser les analyses, suivre les résultats et faciliter la communication entre patients, médecins, laborantins et administrateurs.
Fonctionnalités

Patients : Voir les analyses, télécharger les résultats (PDF/CSV), suivre les factures.  
Médecins : Prescrire des analyses, consulter les résultats.  
Laborantins : Gérer les analyses, enregistrer les résultats.  
Administrateurs : Gérer les utilisateurs, superviser le système.

Technologies

Java et JavaFX pour l’interface et la logique.  
MySQL pour la base de données.  
Maven pour gérer les dépendances.

Installation

Installez Java 11+, Maven et MySQL.  
Clonez le dépôt :  git clone https://github.com/amalfamous/cabinet_analyses_medicales.git


Créez une base de données MySQL :  CREATE DATABASE cabinet;

Configurez la connexion dans persistence.xml (URL, utilisateur, mot de passe).  
Construisez le projet :  mvn clean install



Exécution
Lancez l’application :  
mvn javafx:run

Captures d’Écran
Des images de l’interface (connexion, tableaux de bord) sont dans les pièces jointes du projet.
Licence
Aucune licence spécifiée. Ajoutez-en une (ex. MIT) si vous partagez le projet.
