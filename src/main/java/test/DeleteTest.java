package test;

import dao.*;
import entites.*;

public class DeleteTest {
    public static void main(String[] args) {
        PatientImpl patientDao = new PatientImpl();
        MedecinImpl medecinDao = new MedecinImpl();
        LaborantinImpl laborantinDao = new LaborantinImpl();
        AnalyseImpl analyseDao = new AnalyseImpl();
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        FactureImpl factureDao = new FactureImpl();
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();

        System.out.println("\n=== Suppression des patients ===");
        for (Patient patient : patientDao.findAll()) {
            if (patient.getOrdreAnalyses() != null) {
                patient.getOrdreAnalyses().size();
            }
            patientDao.delete((long) patient.getId());
            System.out.println("Patient supprimé : " + patient);
        }

        // Suppression des factures
        System.out.println("\n=== Suppression des factures ===");
        for (Facture facture : factureDao.findAll()) {
            factureDao.delete((long) facture.getId());
            System.out.println("Facture supprimée : " + facture);
        }

        System.out.println("\n=== Suppression des ordres d'analyse ===");
        for (OrdreAnalyse ordreAnalyse : ordreAnalyseDao.findAll()) {
            ordreAnalyseDao.delete((long) ordreAnalyse.getId());
            System.out.println("Ordre d'analyse supprimé : " + ordreAnalyse);
        }

        System.out.println("\n=== Suppression des résultats d'analyse ===");
        for (ResultatAnalyse resultatAnalyse : resultatAnalyseDao.findAll()) {
            resultatAnalyseDao.delete((long) resultatAnalyse.getId());
            System.out.println("Résultat d'analyse supprimé : " + resultatAnalyse);
        }

        System.out.println("\n=== Suppression des analyses ===");
        for (Analyse analyse : analyseDao.findAll()) {
            analyseDao.delete((long) analyse.getId());
            System.out.println("Analyse supprimée : " + analyse);
        }

        System.out.println("\n=== Suppression des laborantins ===");
        for (Laborantin laborantin : laborantinDao.findAll()) {
            laborantinDao.delete((long) laborantin.getId());
            System.out.println("Laborantin supprimé : " + laborantin);
        }

        System.out.println("\n=== Suppression des médecins ===");
        for (Medecin medecin : medecinDao.findAll()) {
            medecinDao.delete((long) medecin.getId());
            System.out.println("Médecin supprimé : " + medecin);
        }

        System.out.println("\n=== Tous les éléments ont été supprimés ===");
    }
}
