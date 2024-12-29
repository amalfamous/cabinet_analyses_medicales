package test;

import dao.*;
import entites.*;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    /*public static void main(String[] args) {
        // Initialisation des DAO
        PatientImpl patientDao = new PatientImpl();
        MedecinImpl medecinDao = new MedecinImpl();
        LaborantinImpl laborantinDao = new LaborantinImpl();
        AnalyseImpl analyseDao = new AnalyseImpl();
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        FactureImpl factureDao = new FactureImpl();
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();

        // 1. Remplir et insérer des entités

        // Création des patients
        Patient patient1 = new Patient();
        patient1.setNom("Dupont");
        patient1.setPrenom("Jean");
        patient1.setAdresse("123 Rue Principale");
        patient1.setTelephone("0123456789");
        patient1.setEmail("jean.dupont@example.com");
        patient1.setDateNaissance(LocalDateTime.of(1990, 1, 1, 0, 0));
        patientDao.create(patient1);

        Patient patient2 = new Patient();
        patient2.setNom("Martin");
        patient2.setPrenom("Sophie");
        patient2.setAdresse("456 Rue Secondaire");
        patient2.setTelephone("0987654321");
        patient2.setEmail("sophie.martin@example.com");
        patient2.setDateNaissance(LocalDateTime.of(1985, 5, 10, 0, 0));
        patientDao.create(patient2);

        // Création des médecins
        Medecin medecin1 = new Medecin();
        medecin1.setNom("Lemoine");
        medecin1.setPrenom("Alice");
        medecin1.setSpecialite("Cardiologie");
        medecin1.setTelephone("0112233445");
        medecin1.setEmail("alice.lemoine@example.com");
        medecinDao.create(medecin1);

        Medecin medecin2 = new Medecin();
        medecin2.setNom("Durand");
        medecin2.setPrenom("Paul");
        medecin2.setSpecialite("Neurologie");
        medecin2.setTelephone("0223344556");
        medecin2.setEmail("paul.durand@example.com");
        medecinDao.create(medecin2);

        // Création des laborantins
        Laborantin laborantin1 = new Laborantin();
        laborantin1.setNom("Blanc");
        laborantin1.setPrenom("Julien");
        laborantin1.setTelephone("0334455667");
        laborantin1.setEmail("julien.blanc@example.com");
        laborantinDao.create(laborantin1);

        // Création des analyses
        Analyse analyse1 = new Analyse();
        analyse1.setNom("Analyse de sang");
        analyse1.setDescription("Analyse générale des globules rouges et blancs");
        analyse1.setPrix(50.0);
        analyse1.setLaborantin(laborantin1);
        analyseDao.create(analyse1);

        Analyse analyse2 = new Analyse();
        analyse2.setNom("Analyse d'urine");
        analyse2.setDescription("Vérification des infections urinaires");
        analyse2.setPrix(30.0);
        analyse2.setLaborantin(laborantin1);
        analyseDao.create(analyse2);

        // Création d'un ordre d'analyse
        OrdreAnalyse ordre1 = new OrdreAnalyse();
        ordre1.setDateOrdre(LocalDateTime.now());
        ordre1.setPatient(patient1);
        ordre1.setMedecin(medecin1);
        ordreAnalyseDao.create(ordre1);

        // Création d'une facture
        Facture facture1 = new Facture();
        facture1.setMontantTotal(80.0);
        facture1.setDateFacture(LocalDateTime.now());
        facture1.setEtatPaiement(true);
        facture1.setOrdreAnalyse(ordre1);
        factureDao.create(facture1);

        // Création des résultats d'analyse
        ResultatAnalyse resultat1 = new ResultatAnalyse();
        resultat1.setDateResultat(LocalDateTime.now());
        resultat1.setDetailsResultat("Globules rouges : 4.5M/mL, Globules blancs : 6.0K/mL");
        resultat1.setOrdreAnalyse(ordre1);
        resultat1.setAnalyse(analyse1);
        resultat1.getValeurs().put("globules_rouges", 4.5);
        resultat1.getValeurs().put("globules_blancs", 6.0);
        resultatAnalyseDao.create(resultat1);

        // 2. Lire et afficher les données avant la mise à jour
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);


        // 3. Mise à jour des données pour chaque entité
        // Mise à jour d'un patient
        System.out.println("=== Mise à jour d'un patient ===");
        patient1.setAdresse("789 Nouvelle Rue");
        patientDao.update((long) patient1.getId(), patient1);
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);

        // Mise à jour d'un médecin
        System.out.println("=== Mise à jour d'un médecin ===");
        medecin1.setSpecialite("Généraliste");
        medecinDao.update((long) medecin1.getId(), medecin1);
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);

        // Mise à jour d'un laborantin
        System.out.println("=== Mise à jour d'un laborantin ===");
        laborantin1.setNom("Blanc Modifié");
        laborantinDao.update((long) laborantin1.getId(), laborantin1);
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);

        // Mise à jour d'une analyse
        System.out.println("=== Mise à jour d'une analyse ===");
        analyse1.setNom("Analyse de sang Modifiée");
        analyseDao.update((long) analyse1.getId(), analyse1);
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);

        // Mise à jour d'un ordre d'analyse
        System.out.println("=== Mise à jour d'un ordre d'analyse ===");
        ordre1.setDateOrdre(LocalDateTime.now().minusDays(1));
        ordreAnalyseDao.update((long) ordre1.getId(), ordre1);
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);

        // Mise à jour d'une facture
        System.out.println("=== Mise à jour d'une facture ===");
        facture1.setMontantTotal(100.0);
        factureDao.update((long) facture1.getId(), facture1);
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);

        // Mise à jour d'un résultat d'analyse
        System.out.println("=== Mise à jour d'un résultat d'analyse ===");
        resultat1.setDetailsResultat("Globules rouges : 5.0M/mL, Globules blancs : 6.5K/mL");
        resultatAnalyseDao.update((long) resultat1.getId(), resultat1);
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);

        // 4. Suppression d'entités pour chaque type d'entité
        supprimerToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);
    }


    private static void afficherToutesLesEntites(PatientImpl patientDao, MedecinImpl medecinDao, LaborantinImpl laborantinDao,
                                                 AnalyseImpl analyseDao, OrdreAnalyseImpl ordreAnalyseDao, FactureImpl factureDao,
                                                 ResultatAnalyseImpl resultatAnalyseDao) {
        System.out.println("\n=== Liste des patients ===");
        for (Patient p : patientDao.findAll()) {
            System.out.println(p);
        }

        System.out.println("\n=== Liste des médecins ===");
        for (Medecin m : medecinDao.findAll()) {
            System.out.println(m);
        }

        System.out.println("\n=== Liste des laborantins ===");
        for (Laborantin l : laborantinDao.findAll()) {
            System.out.println(l);
        }

        System.out.println("\n=== Liste des analyses ===");
        for (Analyse a : analyseDao.findAll()) {
            System.out.println(a);
        }

        System.out.println("\n=== Liste des ordres d'analyse ===");
        for (OrdreAnalyse o : ordreAnalyseDao.findAll()) {
            System.out.println(o);
        }

        System.out.println("\n=== Liste des factures ===");
        for (Facture f : factureDao.findAll()) {
            System.out.println(f);
        }

        System.out.println("\n=== Liste des résultats d'analyse ===");
        for (ResultatAnalyse r : resultatAnalyseDao.findAll()) {
            System.out.println(r);
        }
    }

    private static void supprimerToutesLesEntites(PatientImpl patientDao, MedecinImpl medecinDao, LaborantinImpl laborantinDao,
                                                  AnalyseImpl analyseDao, OrdreAnalyseImpl ordreAnalyseDao, FactureImpl factureDao,

                                                  ResultatAnalyseImpl resultatAnalyseDao) {
        // Suppression des résultats d'analyse
        for (ResultatAnalyse r : resultatAnalyseDao.findAll()) {
            resultatAnalyseDao.delete((long) r.getId());
        }

        // Suppression des factures
        for (Facture f : factureDao.findAll()) {
            factureDao.delete((long) f.getId());
        }

        // Suppression des ordres d'analyse
        for (OrdreAnalyse o : ordreAnalyseDao.findAll()) {
            ordreAnalyseDao.delete((long) o.getId());
        }

        // Suppression des analyses
        for (Analyse a : analyseDao.findAll()) {
            analyseDao.delete((long) a.getId());
        }

        // Suppression des laborantins
        for (Laborantin l : laborantinDao.findAll()) {
            laborantinDao.delete((long) l.getId());
        }

        // Suppression des médecins
        for (Medecin m : medecinDao.findAll()) {
            medecinDao.delete((long) m.getId());
        }

        // Suppression des patients
        for (Patient p : patientDao.findAll()) {
            patientDao.delete((long) p.getId());
        }

        // Vérification des suppressions
        afficherToutesLesEntites(patientDao, medecinDao, laborantinDao, analyseDao, ordreAnalyseDao, factureDao, resultatAnalyseDao);
    }
*/
}
