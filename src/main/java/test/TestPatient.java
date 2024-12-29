package test;

import dao.PatientImpl;
import entites.Patient;

import java.time.LocalDateTime;

public class TestPatient {
    public static void main(String[] args) {
        PatientImpl patientDao = new PatientImpl();
        Patient patient = new Patient();
        patient.setNom("Dupont");
        patient.setPrenom("Jean");
        patient.setAdresse("123 Rue de Paris");
        patient.setTelephone("0123456789");
        patient.setEmail("jean.dupont@example.com");
        patient.setDateNaissance(LocalDateTime.of(1990, 5, 15, 0, 0));
        patient.setOrdreAnalyses(null);
        System.out.println("Création d'un nouveau patient...");
        patientDao.create(patient);

        System.out.println("\nListe des patients enregistrés :");
        patientDao.findAll().forEach(p -> System.out.println(
                "ID: " + p.getId() + ", Nom: " + p.getNom() + ", Prénom: " + p.getPrenom()+p.getTelephone()+p.getOrdreAnalyses()+p.getEmail()));

        System.out.println("\nMise à jour du patient...");
        patient.setPrenom("Jean-Marie");
        patient.setAdresse("456 Avenue des Champs");
        patientDao.update((long) patient.getId(), patient);
        System.out.println("\nRécupération du patient par ID...");
        Patient updatedPatient = patientDao.findById((long) patient.getId());
        System.out.println("Patient après mise à jour: Nom: " + updatedPatient.getNom() +
                ", Prénom: " + updatedPatient.getPrenom() +
                ", Adresse: " + updatedPatient.getAdresse());
        System.out.println("\nSuppression du patient...");
        try {
            patientDao.delete((long) patient.getId());
            System.out.println("Patient supprimé avec succès.");
        } catch (RuntimeException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
        System.out.println("\nListe des patients après suppression :");
        patientDao.findAll().forEach(p -> System.out.println(
                "ID: " + p.getId() + ", Nom: " + p.getNom() + ", Prénom: " + p.getPrenom()));
    }
}
