package test;

import dao.*;
import entites.*;

public class FindAllTest {
    public static void main(String[] args) {
        PatientImpl patientDao = new PatientImpl();
        MedecinImpl medecinDao = new MedecinImpl();
        LaborantinImpl laborantinDao = new LaborantinImpl();
        AnalyseImpl analyseDao = new AnalyseImpl();
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        FactureImpl factureDao = new FactureImpl();
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();

        System.out.println("=== Liste des patients ===");
        for (Patient patient : patientDao.findAll()) {
            System.out.println(patient);
        }

        System.out.println("\n=== Liste des médecins ===");
        for (Medecin medecin : medecinDao.findAll()) {
            System.out.println(medecin);
        }

        System.out.println("\n=== Liste des laborantins ===");
        for (Laborantin laborantin : laborantinDao.findAll()) {
            System.out.println(laborantin);
        }

        System.out.println("\n=== Liste des analyses ===");
        for (Analyse analyse : analyseDao.findAll()) {
            System.out.println(analyse);
        }

        System.out.println("\n=== Liste des ordres d'analyse ===");
        for (OrdreAnalyse ordreAnalyse : ordreAnalyseDao.findAll()) {
            System.out.println(ordreAnalyse);
        }

        System.out.println("\n=== Liste des factures ===");
        for (Facture facture : factureDao.findAll()) {
            System.out.println(facture);
        }

        System.out.println("\n=== Liste des résultats d'analyse ===");
        for (ResultatAnalyse resultatAnalyse : resultatAnalyseDao.findAll()) {
            System.out.println(resultatAnalyse);
        }
    }
}
