package test;

import dao.LaborantinImpl;
import entites.Laborantin;

import java.util.List;

public class TestLaborantin {

    public static void main(String[] args) {
        LaborantinImpl laborantinDao = new LaborantinImpl();
        System.out.println("=== Test: CREATE ===");
        Laborantin laborantin = new Laborantin();
        laborantin.setNom("Dupont");
        laborantin.setPrenom("Jean");
        laborantin.setTelephone("0123456789");
        laborantin.setEmail("jean.dupont@example.com");

        laborantinDao.create(laborantin);
        System.out.println("Laborantin créé avec succès.");

        System.out.println("\n=== Données après insertion ===");
        laborantinDao.findAll().forEach(l -> {
            System.out.println("ID: " + l.getId() + ", Nom: " + l.getNom() + ", Prénom: " + l.getPrenom());
        });

        System.out.println("\n=== Test: UPDATE ===");
        Laborantin laborantinToUpdate = laborantinDao.findById((long) laborantin.getId());
        if (laborantinToUpdate != null) {
            laborantinToUpdate.setNom("Dupont - Mise à jour");
            laborantinToUpdate.setPrenom("Jean - Mise à jour");
            laborantinDao.update((long) laborantinToUpdate.getId(), laborantinToUpdate); // Mise à jour du Laborantin
            System.out.println("Laborantin mis à jour avec succès.");
        } else {
            System.out.println("Laborantin non trouvé.");
        }

        System.out.println("\n=== Données après mise à jour ===");
        laborantinDao.findAll().forEach(l -> {
            System.out.println("ID: " + l.getId() + ", Nom: " + l.getNom() + ", Prénom: " + l.getPrenom());
        });

        System.out.println("\n=== Test: DELETE ===");
        Laborantin laborantinToDelete = laborantinDao.findById((long) laborantin.getId());
        if (laborantinToDelete != null) {
            laborantinDao.delete((long) laborantinToDelete.getId()); // Suppression du Laborantin
            System.out.println("Laborantin supprimé avec succès.");
        } else {
            System.out.println("Laborantin non trouvé.");
        }

        System.out.println("\n=== Données après suppression ===");
        laborantinDao.findAll().forEach(l -> {
            System.out.println("ID: " + l.getId() + ", Nom: " + l.getNom() + ", Prénom: " + l.getPrenom());
        });
    }
}
