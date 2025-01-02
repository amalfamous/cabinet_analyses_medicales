package dao;

import entites.Analyse;
import entites.ResultatAnalyse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class AnalyseImpl implements IDao<Analyse> {
    private EntityManagerFactory emf;//bax n gerer les cnx a la bd
    private EntityManager em;//exécuter des requêtes + les enregistrer dans BD

    public AnalyseImpl() {
        emf = Persistence.createEntityManagerFactory("cabinet");
        em = emf.createEntityManager();
    }

    @Override
    public void create(Analyse analyse) {
        try {
            em.getTransaction().begin();
            if (analyse.getLaborantin() != null) {
                analyse.setLaborantin(em.merge(analyse.getLaborantin()));
            }
            if (analyse.getResultatAnalyses() != null) {
                analyse.setResultatAnalyses(em.merge(analyse.getResultatAnalyses()));
            }
            em.persist(analyse);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, Analyse analyse) {
        em.getTransaction().begin();
        Analyse analyse1 = em.find(Analyse.class, id);
        if (analyse1 != null) {
            if (analyse.getNom() != null) analyse1.setNom(analyse.getNom());
            if (analyse.getPrix() != null) analyse1.setPrix(analyse.getPrix());
            if (analyse.getDescription() != null) analyse1.setDescription(analyse.getDescription());

            if (analyse.getResultatAnalyses() != null) {
                List<ResultatAnalyse> updatedResults = new ArrayList<>();
                for (ResultatAnalyse resultat : analyse.getResultatAnalyses()) {
                    // Merge chaque élément individuellement
                    updatedResults.add(em.merge(resultat));
                }
                analyse1.setResultatAnalyses(updatedResults);
            }

            if (analyse.getLaborantin() != null) {
                // Réattacher le laborantin si présent
                analyse1.setLaborantin(em.merge(analyse.getLaborantin()));
            }
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Analyse analyse = em.find(Analyse.class, id);
        if (analyse != null) {
            if (analyse.getLaborantin() != null) {
                analyse.setLaborantin(null);
                em.merge(analyse);
            }
            if (analyse.getResultatAnalyses() != null) {
                for (ResultatAnalyse resultatAnalyse : analyse.getResultatAnalyses()) {
                    resultatAnalyse.setAnalyse(null);
                    em.merge(resultatAnalyse);
                }
            }
            em.remove(analyse);
        }
        em.getTransaction().commit();
    }

    @Override
    public Analyse findById(Long id) {
        return em.find(Analyse.class, id);
    }

    @Override
    public List<Analyse> findAll() {
        return em.createQuery("SELECT a FROM Analyse a", Analyse.class).getResultList();
    }


}
