package dao;

import entites.Facture;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class FactureImpl  implements IDao<Facture> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public FactureImpl(){
        emf= Persistence.createEntityManagerFactory("cabinet");
        em=emf.createEntityManager();
    }


    @Override
    public void create(Facture facture) {
        try{
            em.getTransaction().begin();
            if (facture.getOrdreAnalyse() != null) {
                facture.setOrdreAnalyse(em.merge(facture.getOrdreAnalyse()));
            }
            em.persist(facture);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, Facture facture) {
        em.getTransaction().begin();
        Facture f=em.find(Facture.class,id);
        if (f!=null){
            if (facture.getMontantTotal()!=null) f.setMontantTotal(facture.getMontantTotal());
            if (facture.getDateFacture()!=null) f.setDateFacture(facture.getDateFacture());
            if (facture.getOrdreAnalyse()!=null) f.setOrdreAnalyse(facture.getOrdreAnalyse());
            if (facture.isEtatPaiement()!=f.isEtatPaiement()) f.setEtatPaiement(facture.isEtatPaiement());
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Facture f=em.find(Facture.class,id);
        if (f!=null){
            if (f.getOrdreAnalyse() != null) {
                f.getOrdreAnalyse().setFacture(null);
                em.merge(f.getOrdreAnalyse());
            }
            em.remove(f);
        }
        em.getTransaction().commit();
    }

    @Override
    public Facture findById(Long id) {
        return em.find(Facture.class, id);
    }

    @Override
    public List<Facture> findAll() {
        return em.createQuery("SELECT f FROM Facture f", Facture.class).getResultList();
    }
}
