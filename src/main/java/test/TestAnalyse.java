package test;

import dao.AnalyseImpl;
import entites.Analyse;

public class TestAnalyse {

    public static void main(String[] args) {
        AnalyseImpl analyseDao = new AnalyseImpl();

        System.out.println("=== Test: CREATE ===");
        Analyse analyse = new Analyse();
        analyse.setNom("Analyse de sang");
        analyse.setDescription("Analyse pour vérifier les niveaux de vitamine D");
        analyse.setPrix(120.0);

        analyse.setLaborantin(null);  // Laborantin est à null

        analyseDao.create(analyse);
        System.out.println("Analyse créée avec succès.");

        System.out.println("\n=== Données après insertion ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });

        System.out.println("\n=== Test: UPDATE ===");
        Analyse analyseToUpdate = analyseDao.findById(Long.valueOf(analyse.getId()));  // Utiliser Long
        if (analyseToUpdate != null) {
            analyseToUpdate.setNom("Analyse de sang - Mise à jour");
            analyseToUpdate.setPrix(190.0);
            analyseDao.update(Long.valueOf(analyseToUpdate.getId()), analyseToUpdate);  // Utiliser Long
            System.out.println("Analyse mise à jour avec succès.");
        } else {
            System.out.println("Aucune analyse trouvée avec l'ID spécifié.");
        }

        System.out.println("\n=== Données après mise à jour ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });

       /* // Test: DELETE
        System.out.println("\n=== Test: DELETE ===");
        analyseDao.delete(Long.valueOf(analyse.getId()));  // Utiliser Long
        System.out.println("Analyse supprimée avec succès.");
*/
        System.out.println("\n=== Données après suppression ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });

        System.out.println("\n=== Vérification finale : FIND ALL ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });
    }
}
