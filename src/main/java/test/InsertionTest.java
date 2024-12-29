package test;

import dao.*;
import entites.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class InsertionTest {
    public static void main(String[] args) {
        PatientImpl patientDao = new PatientImpl();
        MedecinImpl medecinDao = new MedecinImpl();
        LaborantinImpl laborantinDao = new LaborantinImpl();
        AnalyseImpl analyseDao = new AnalyseImpl();
        OrdreAnalyseImpl ordreAnalyseDao = new OrdreAnalyseImpl();
        FactureImpl factureDao = new FactureImpl();
        ResultatAnalyseImpl resultatAnalyseDao = new ResultatAnalyseImpl();

        try {
            Patient patient = new Patient();
            patient.setNom("Dupont");
            patient.setPrenom("Jean");
            patient.setAdresse("123 Rue de la Santé");
            patient.setTelephone("0123456789");
            patient.setEmail("jean.dupont@example.com");
            patient.setDateNaissance(LocalDateTime.of(1985, 5, 20, 0, 0));
            patientDao.create(patient);

            Medecin medecin = new Medecin();
            medecin.setNom("Martin");
            medecin.setPrenom("Claire");
            medecin.setSpecialite("Biologiste");
            medecin.setTelephone("0987654321");
            medecin.setEmail("claire.martin@example.com");
            medecinDao.create(medecin);

            Laborantin laborantin = new Laborantin();
            laborantin.setNom("Morel");
            laborantin.setPrenom("Julie");
            laborantin.setTelephone("0456789123");
            laborantin.setEmail("julie.morel@example.com");
            laborantinDao.create(laborantin);

            Analyse analyse = new Analyse();
            analyse.setNom("Hémogramme");
            analyse.setDescription("Analyse sanguine complète");
            analyse.setPrix(30.0);
            analyse.setLaborantin(laborantin);
            analyseDao.create(analyse);

            OrdreAnalyse ordreAnalyse = new OrdreAnalyse();
            ordreAnalyse.setDateOrdre(LocalDateTime.now());
            ordreAnalyse.setPatient(patient);
            ordreAnalyse.setMedecin(medecin);
            ordreAnalyseDao.create(ordreAnalyse);

            Facture facture = new Facture();
            facture.setMontantTotal(100.0);
            facture.setDateFacture(LocalDateTime.now());
            facture.setEtatPaiement(false);
            facture.setOrdreAnalyse(ordreAnalyse);
            factureDao.create(facture);

            ResultatAnalyse resultatAnalyse = new ResultatAnalyse();
            resultatAnalyse.setDateResultat(LocalDateTime.now());
            resultatAnalyse.setDetailsResultat("Analyse normale");
            Map<String, Double> valeurs = new HashMap<>();
            valeurs.put("Glucose", 5.6);
            valeurs.put("Hémoglobine", 13.2);
            resultatAnalyse.setValeurs(valeurs);
            resultatAnalyse.setAnalyse(analyse);
            resultatAnalyse.setOrdreAnalyse(ordreAnalyse);
            resultatAnalyseDao.create(resultatAnalyse);

            System.out.println("Toutes les entités ont été insérées avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'insertion des entités.");
        }
    }
}
