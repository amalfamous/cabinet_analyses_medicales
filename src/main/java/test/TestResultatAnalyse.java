package test;

import dao.ResultatAnalyseImpl;
import entites.ResultatAnalyse;

import java.time.LocalDateTime;

public class TestResultatAnalyse {
    public static void main(String[] args) {
        ResultatAnalyseImpl dao = new ResultatAnalyseImpl();
        System.out.println("Création d'un ResultatAnalyse avec Analyse et OrdreAnalyse null...");
        ResultatAnalyse resultatAnalyse = new ResultatAnalyse();
        resultatAnalyse.setDateResultat(LocalDateTime.now());
        resultatAnalyse.setDetailsResultat("Résultat de test");

        resultatAnalyse.getValeurs().put("TestKey", 123.45);
        dao.create(resultatAnalyse);
        System.out.println("RésultatAnalyse créé.");

        System.out.println("Lecture du ResultatAnalyse avec ID = " + resultatAnalyse.getId());
        ResultatAnalyse foundResultat = dao.findById((long) resultatAnalyse.getId());
        //System.out.println("Résultat trouvé: " + foundResultat);
        if (foundResultat != null) {
            System.out.println("ResultatAnalyse ID: " + foundResultat.getId());
        } else {
            System.out.println("No ResultatAnalyse found.");
        }

        System.out.println("Mise à jour du ResultatAnalyse...");
        if (foundResultat != null) {
            foundResultat.setDetailsResultat("Résultat mis à jour");
            foundResultat.getValeurs().put("TestKeyUpdated", 987.65);

            dao.update((long) foundResultat.getId(), foundResultat);
            System.out.println("RésultatAnalyse mis à jour.");
        }

        System.out.println("Suppression du ResultatAnalyse avec ID = " + foundResultat.getId());
        if (foundResultat != null) {
            dao.delete((long) foundResultat.getId());
            System.out.println("RésultatAnalyse supprimé.");
        }

        ResultatAnalyse deletedResultat = dao.findById((long) foundResultat.getId());
        if (deletedResultat == null) {
            System.out.println("Le ResultatAnalyse a été supprimé avec succès.");
        } else {
            System.out.println("Le ResultatAnalyse existe toujours.");
        }
    }
}
