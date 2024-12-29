package test;

import dao.MedecinImpl;
import entites.Medecin;

import java.util.List;

public class TestMedecin {
    public static void main(String[] args) {
        MedecinImpl medecinDao = new MedecinImpl();

        System.out.println("=== Création d'un nouveau médecin ===");
        Medecin medecin = new Medecin();
        medecin.setNom("Dupont");
        medecin.setPrenom("Alice");
        medecin.setSpecialite("Dermatologie");
        medecin.setTelephone("0606060606");
        medecin.setEmail("alice.dupont@example.com");
        medecin.setOrdreAnalyse(null);
        medecinDao.create(medecin);

        System.out.println("=== Liste des médecins enregistrés ===");
        List<Medecin> medecins = medecinDao.findAll();
        for (Medecin m : medecins) {
            System.out.println("ID: " + m.getId() + ", Nom: " + m.getNom() + ", Prénom: " + m.getPrenom());
        }

        System.out.println("\n=== Mise à jour du médecin ===");
        if (!medecins.isEmpty()) {
            Medecin medecinToUpdate = medecins.get(0);
            medecinToUpdate.setPrenom("Alice-Marie");
            medecinToUpdate.setSpecialite("Pédiatrie");
            medecinDao.update((long) medecinToUpdate.getId(), medecinToUpdate);

            Medecin updatedMedecin = medecinDao.findById((long) medecinToUpdate.getId());
            System.out.println("Médecin après mise à jour: Nom: " + updatedMedecin.getNom() + ", Prénom: " + updatedMedecin.getPrenom() + ", Spécialité: " + updatedMedecin.getSpecialite());
        }

        System.out.println("\n=== Suppression du médecin ===");
        if (!medecins.isEmpty()) {
            Medecin medecinToDelete = medecins.get(0);
            try {
                medecinDao.delete((long) medecinToDelete.getId());
                System.out.println("Médecin supprimé avec succès.");
            } catch (RuntimeException e) {
                System.err.println("Erreur lors de la suppression : " + e.getMessage());
            }
        }

        System.out.println("\n=== Liste des médecins après suppression ===");
        medecins = medecinDao.findAll();
        for (Medecin m : medecins) {
            System.out.println("ID: " + m.getId() + ", Nom: " + m.getNom() + ", Prénom: " + m.getPrenom());
        }
    }
}
