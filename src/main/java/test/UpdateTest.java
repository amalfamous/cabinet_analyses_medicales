package test;

import dao.*;
import entites.*;

import java.time.LocalDateTime;

public class UpdateTest {
    public static void main(String[] args) {
        PatientImpl patientDao = new PatientImpl();
        MedecinImpl medecinDao = new MedecinImpl();
        LaborantinImpl laborantinDao = new LaborantinImpl();
        AnalyseImpl analyseDao = new AnalyseImpl();
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        FactureImpl factureDao = new FactureImpl();
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();
        Patient patient = patientDao.findById(1L);
        if (patient != null) {
            patient.setAdresse("789 Rue Modifiée");
            patient.setTelephone("0112233445");
            patientDao.update((long) patient.getId(), patient);
            System.out.println("Patient mis à jour : " + patient);
        }

        Medecin medecin = medecinDao.findById(1L);
        if (medecin != null) {
            medecin.setSpecialite("Généraliste");
            medecin.setTelephone("0556677889");
            medecinDao.update((long) medecin.getId(), medecin);
            System.out.println("Médecin mis à jour : " + medecin);
        }

        Laborantin laborantin = laborantinDao.findById(1L);
        if (laborantin != null) {
            laborantin.setEmail("laborantin.update@example.com");
            laborantinDao.update((long) laborantin.getId(), laborantin);
            System.out.println("Laborantin mis à jour : " + laborantin);
        }

        Analyse analyse = analyseDao.findById(1L);
        if (analyse != null) {
            analyse.setPrix(60.0);
            analyse.setDescription("Description mise à jour");
            analyseDao.update((long) analyse.getId(), analyse);
            System.out.println("Analyse mise à jour : " + analyse);
        }

        OrdreAnalyse ordreAnalyse = ordreAnalyseDao.findById(1L);
        if (ordreAnalyse != null) {
            ordreAnalyse.setDateOrdre(LocalDateTime.now().minusDays(1));
            ordreAnalyseDao.update((long) ordreAnalyse.getId(), ordreAnalyse);
            System.out.println("Ordre d'analyse mis à jour : " + ordreAnalyse);
        }

        Facture facture = factureDao.findById(1L);
        if (facture != null) {
            facture.setMontantTotal(120.0);
            facture.setEtatPaiement(false);
            factureDao.update((long) facture.getId(), facture);
            System.out.println("Facture mise à jour : " + facture);
        }

        ResultatAnalyse resultatAnalyse = resultatAnalyseDao.findById(1L);
        if (resultatAnalyse != null) {
            resultatAnalyse.setDetailsResultat("Mise à jour des résultats");
            resultatAnalyse.getValeurs().put("nouveau_parametre", 7.0);
            resultatAnalyseDao.update((long) resultatAnalyse.getId(), resultatAnalyse);
            System.out.println("Résultat d'analyse mis à jour : " + resultatAnalyse);
        }

        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);
    }

    private static void afficherToutesLesEntites(PatientImpl patientDao, MedecinImpl medecinDao, LaborantinImpl laborantinDao,
                                                 AnalyseImpl analyseDao, OrdreAnalyseImpl ordreAnalyseDao, FactureImpl factureDao,
                                                 ResultatAnalyseImpl resultatAnalyseDao) {
        System.out.println("\n=== Liste des patients après mise à jour ===");
        for (Patient p : patientDao.findAll()) {
            System.out.println(p);
        }

        System.out.println("\n=== Liste des médecins après mise à jour ===");
        for (Medecin m : medecinDao.findAll()) {
            System.out.println(m);
        }

        System.out.println("\n=== Liste des laborantins après mise à jour ===");
        for (Laborantin l : laborantinDao.findAll()) {
            System.out.println(l);
        }

        System.out.println("\n=== Liste des analyses après mise à jour ===");
        for (Analyse a : analyseDao.findAll()) {
            System.out.println(a);
        }

        System.out.println("\n=== Liste des ordres d'analyse après mise à jour ===");
        for (OrdreAnalyse o : ordreAnalyseDao.findAll()) {
            System.out.println(o);
        }

        System.out.println("\n=== Liste des factures après mise à jour ===");
        for (Facture f : factureDao.findAll()) {
            System.out.println(f);
        }

        System.out.println("\n=== Liste des résultats d'analyse après mise à jour ===");
        for (ResultatAnalyse r : resultatAnalyseDao.findAll()) {
            System.out.println(r);
        }
    }
}
