package test;

import dao.UserImpl;
import entites.User;
import entites.Patient;
import entites.Laborantin;

public class TestUser {

    public static void main(String[] args) {
        UserImpl userDao = new UserImpl();

        // Test de création d'un utilisateur
        System.out.println("=== Test de création ===");
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123"); // Le mot de passe sera hashé avant d'être enregistré
        user.setRole("ROLE_PATIENT");

        // Création d'un patient associé
        Patient patient = new Patient();
        patient.setNom("Doe");
        patient.setPrenom("John");
        patient.setAdresse("123 Rue Exemple");
        patient.setTelephone("0123456789");
        patient.setEmail("johndoe@example.com");
        user.setPatient(patient);

        userDao.create(user);
        System.out.println("Utilisateur créé avec succès : " + user);

        // Test de mise à jour
        System.out.println("=== Test de mise à jour ===");
        User existingUser = userDao.findById((long) user.getId());
        if (existingUser != null) {
            existingUser.setUsername("updateduser");
            existingUser.setPassword("newpassword"); // Le mot de passe sera re-hashé
            userDao.update((long) existingUser.getId(), existingUser);
            System.out.println("Utilisateur mis à jour : " + existingUser);
        } else {
            System.out.println("Utilisateur non trouvé pour la mise à jour.");
        }

        // Test d'authentification
        System.out.println("=== Test d'authentification ===");
        User authenticatedUser = userDao.authenticate("updateduser", "newpassword");
        if (authenticatedUser != null) {
            System.out.println("Authentification réussie pour : " + authenticatedUser);
        } else {
            System.out.println("Échec de l'authentification.");
        }

        // Test de suppression
        System.out.println("=== Test de suppression ===");
        userDao.delete((long) user.getId());
        User deletedUser = userDao.findById((long) user.getId());
        if (deletedUser == null) {
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            System.out.println("Échec de la suppression.");
        }

        // Test de récupération de tous les utilisateurs
        System.out.println("=== Test de récupération de tous les utilisateurs ===");
        User anotherUser = new User();
        anotherUser.setUsername("user2");
        anotherUser.setPassword("password2");
        anotherUser.setRole("ROLE_LABORANTIN");

        Laborantin laborantin = new Laborantin();
        laborantin.setNom("Smith");
        laborantin.setPrenom("Jane");
        anotherUser.setLaborantin(laborantin);

        userDao.create(anotherUser);
        System.out.println("Deuxième utilisateur créé : " + anotherUser);

        System.out.println("Liste de tous les utilisateurs :");
        for (User u : userDao.findAll()) {
            System.out.println(u);
        }
    }
}
