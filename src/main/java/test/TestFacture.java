package test;

import dao.FactureImpl;
import entites.Facture;
import entites.OrdreAnalyse;
import java.time.LocalDateTime;
import java.util.List;

public class TestFacture {
    public static void main(String[] args) {
        FactureImpl factureDao = new FactureImpl();
        System.out.println("=== Test: CREATE ===");
        OrdreAnalyse ordreAnalyse = new OrdreAnalyse();
        ordreAnalyse.setDateOrdre(LocalDateTime.now());

        Facture facture1 = new Facture();
        facture1.setDateFacture(LocalDateTime.now());
        facture1.setMontantTotal(1500.50);
        facture1.setEtatPaiement(false);
        facture1.setOrdreAnalyse(ordreAnalyse);
        factureDao.create(facture1);

        System.out.println("=== Test: UPDATE - Mettre ordreAnalyse à null ===");
        facture1.setOrdreAnalyse(null);
        factureDao.update((long) facture1.getId(), facture1);

        System.out.println("Facture mise à jour avec ordreAnalyse à null.");

        List<Facture> factures = factureDao.findAll();
        System.out.println("=== Données après mise à jour ===");
        for (Facture f : factures) {
            System.out.println("ID: " + f.getId() + ", Montant Total: " + f.getMontantTotal() +
                    ", Etat Paiement: " + f.isEtatPaiement() +
                    ", OrdreAnalyse: " + (f.getOrdreAnalyse() != null ? f.getOrdreAnalyse().getId() : "null"));
        }

        System.out.println("=== Test: DELETE ===");
        if (!factures.isEmpty()) {
            Facture factureToDelete = factures.get(0);
            factureDao.delete((long) factureToDelete.getId());

            System.out.println("Facture supprimée avec succès.");

            factures = factureDao.findAll();
            System.out.println("=== Données après suppression ===");
            for (Facture f : factures) {
                System.out.println("ID: " + f.getId() + ", Montant Total: " + f.getMontantTotal() +
                        ", Etat Paiement: " + f.isEtatPaiement() +
                        ", OrdreAnalyse: " + (f.getOrdreAnalyse() != null ? f.getOrdreAnalyse().getId() : "null"));
            }
        }
    }
}
