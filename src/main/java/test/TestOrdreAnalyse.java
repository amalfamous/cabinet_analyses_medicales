package test;

import dao.OrdreAnalyseImpl;
import entites.Medecin;
import entites.OrdreAnalyse;
import entites.Patient;

import java.time.LocalDateTime;
import java.util.List;

public class TestOrdreAnalyse {
    public static void main(String[] args) {
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        System.out.println("=== Création d'un nouvel ordre d'analyse ===");
        OrdreAnalyse ordreAnalyse = new OrdreAnalyse();
        ordreAnalyse.setDateOrdre(LocalDateTime.now());

        Patient patient = new Patient();
        patient.setNom("Doe");
        patient.setPrenom("John");
        patient.setTelephone("0123456789");
        ordreAnalyse.setPatient(patient);

        Medecin medecin = new Medecin();
        medecin.setNom("Smith");
        medecin.setPrenom("Jane");
        medecin.setSpecialite("Radiologie");
        ordreAnalyse.setMedecin(medecin);
        ordreAnalyseDao.create(ordreAnalyse);
        System.out.println("=== Liste des ordres d'analyse enregistrés ===");
        List<OrdreAnalyse> ordreAnalyses = ordreAnalyseDao.findAll();
        for (OrdreAnalyse o : ordreAnalyses) {
            System.out.println("ID: " + o.getId() + ", Date: " + o.getDateOrdre() +
                    ", Patient: " + o.getPatient().getNom() + ", Médecin: " + o.getMedecin().getNom());
        }

        System.out.println("\n=== Mise à jour de l'ordre d'analyse ===");
        if (!ordreAnalyses.isEmpty()) {
            OrdreAnalyse ordreToUpdate = ordreAnalyses.get(0);
            ordreToUpdate.setDateOrdre(LocalDateTime.now().minusDays(1));
            ordreAnalyseDao.update((long) ordreToUpdate.getId(), ordreToUpdate);

            OrdreAnalyse updatedOrdre = ordreAnalyseDao.findById((long) ordreToUpdate.getId());
            System.out.println("Ordre mis à jour: ID: " + updatedOrdre.getId() +
                    ", Date: " + updatedOrdre.getDateOrdre());
        }

        System.out.println("\n=== Suppression de l'ordre d'analyse ===");
        if (!ordreAnalyses.isEmpty()) {
            OrdreAnalyse ordreToDelete = ordreAnalyses.get(0);
            try {
                ordreAnalyseDao.delete((long) ordreToDelete.getId());
                System.out.println("Ordre d'analyse supprimé avec succès.");
            } catch (RuntimeException e) {
                System.out.println("Erreur lors de la suppression : " + e.getMessage());
            }
        }

        System.out.println("\n=== Liste des ordres d'analyse après suppression ===");
        List<OrdreAnalyse> ordreAnalysesAfterDelete = ordreAnalyseDao.findAll();
        for (OrdreAnalyse o : ordreAnalysesAfterDelete) {
            System.out.println("ID: " + o.getId() + ", Date: " + o.getDateOrdre());
        }
    }
}
