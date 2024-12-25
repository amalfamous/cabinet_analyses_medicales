import dao.AnalyseImpl;
import entites.Analyse;

public class TestAnalyse {

    public static void main(String[] args) {
        // Instancier le DAO
        AnalyseImpl analyseDao = new AnalyseImpl();

        // Test: CREATE
        System.out.println("=== Test: CREATE ===");
        Analyse analyse = new Analyse();
        analyse.setNom("Analyse de sang");
        analyse.setDescription("Analyse pour vérifier les niveaux de vitamine D");
        analyse.setPrix(120.0);

        // Laborantin sera null pour l'instant
        analyse.setLaborantin(null);  // Laborantin est à null

        // Insérer l'analyse dans la base de données
        analyseDao.create(analyse);
        System.out.println("Analyse créée avec succès.");

        // Afficher les données de la base de données après insertion
        System.out.println("\n=== Données après insertion ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });

        // Test: UPDATE
        System.out.println("\n=== Test: UPDATE ===");
        // Utilisation du Long pour l'ID
        Analyse analyseToUpdate = analyseDao.findById(Long.valueOf(analyse.getId()));  // Utiliser Long
        if (analyseToUpdate != null) {
            analyseToUpdate.setNom("Analyse de sang - Mise à jour");
            analyseToUpdate.setPrix(150.0);
            analyseDao.update(Long.valueOf(analyseToUpdate.getId()), analyseToUpdate);  // Utiliser Long
            System.out.println("Analyse mise à jour avec succès.");
        } else {
            System.out.println("Aucune analyse trouvée avec l'ID spécifié.");
        }

        // Afficher les données de la base de données après mise à jour
        System.out.println("\n=== Données après mise à jour ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });

       /* // Test: DELETE
        System.out.println("\n=== Test: DELETE ===");
        analyseDao.delete(Long.valueOf(analyse.getId()));  // Utiliser Long
        System.out.println("Analyse supprimée avec succès.");
*/
        // Afficher les données de la base de données après suppression
        System.out.println("\n=== Données après suppression ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });

        // Vérification finale: FIND ALL après suppression
        System.out.println("\n=== Vérification finale : FIND ALL ===");
        analyseDao.findAll().forEach(a -> {
            System.out.println("ID: " + a.getId() + ", Nom: " + a.getNom() + ", Prix: " + a.getPrix());
        });
    }
}
