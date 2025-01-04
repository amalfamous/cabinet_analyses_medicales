package dao;

import entites.ResultatAnalyse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultatAnalyseImpl implements IDao<ResultatAnalyse>{
    private EntityManagerFactory emf;
    private EntityManager em;

    public ResultatAnalyseImpl() {
        emf = Persistence.createEntityManagerFactory("cabinet");
        em = emf.createEntityManager();
    }

    @Override
    public void create(ResultatAnalyse resultatAnalyse) {
        try {
            if (resultatAnalyse.getValeurs() == null) {
                resultatAnalyse.setValeurs(new HashMap<>());
            }

            if (!resultatAnalyse.getValeurs().containsKey("parametre")) {
                resultatAnalyse.getValeurs().put("parametre", 0.0);
            }

            if (resultatAnalyse.getAnalyse() != null) {
                resultatAnalyse.setAnalyse(em.merge(resultatAnalyse.getAnalyse()));
            }
            if (resultatAnalyse.getOrdreAnalyse() != null) {
                resultatAnalyse.setOrdreAnalyse(em.merge(resultatAnalyse.getOrdreAnalyse()));
            }

            em.getTransaction().begin();
            em.persist(resultatAnalyse);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void update(Long id, ResultatAnalyse resultatAnalyse) {
        try {
            em.getTransaction().begin();
            ResultatAnalyse resultatAnalyse1 = em.find(ResultatAnalyse.class, id);
            if (resultatAnalyse1 != null) {
                if (resultatAnalyse.getDateResultat() != null) resultatAnalyse1.setDateResultat(resultatAnalyse.getDateResultat());
                if (resultatAnalyse.getOrdreAnalyse() != null) resultatAnalyse1.setOrdreAnalyse(resultatAnalyse.getOrdreAnalyse());
                if (resultatAnalyse.getValeurs() != null) {
                    resultatAnalyse1.setValeurs(new HashMap<>(resultatAnalyse.getValeurs()));
                }
                if (resultatAnalyse.getAnalyse() != null) resultatAnalyse1.setAnalyse(resultatAnalyse.getAnalyse());
                if (resultatAnalyse.getDetailsResultat() != null) resultatAnalyse1.setDetailsResultat(resultatAnalyse.getDetailsResultat());
                System.out.println("Avant mise à jour dans la base : " + resultatAnalyse1);
                em.merge(resultatAnalyse1);
                em.flush();//Forcer la synchronisation avec la base de données
                System.out.println("Après mise à jour dans la base : " + resultatAnalyse1);
            } else {
                throw new IllegalArgumentException("Aucun résultat trouvé avec l'id : " + id);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            throw e;
        }
    }

    /* @Override
    public void update(Long id, ResultatAnalyse resultatAnalyse) {
        em.getTransaction().begin();
        ResultatAnalyse resultatAnalyse1 = em.find(ResultatAnalyse.class, id);
        if (resultatAnalyse1 != null) {
            if (resultatAnalyse.getDateResultat() != null) resultatAnalyse1.setDateResultat(resultatAnalyse.getDateResultat());
            if (resultatAnalyse.getOrdreAnalyse() != null) resultatAnalyse1.setOrdreAnalyse(resultatAnalyse.getOrdreAnalyse());

*//*
            if (resultatAnalyse.getValeurs() != null) resultatAnalyse1.setValeurs(resultatAnalyse.getValeurs());
*//*
          *//*  if (resultatAnalyse.getValeurs() != null) {
                if (!resultatAnalyse.getValeurs().containsKey("parametre")) {
                    resultatAnalyse.getValeurs().put("parametre", 0.0);
                }
                resultatAnalyse1.setValeurs(resultatAnalyse.getValeurs());
            }*//*
            if (resultatAnalyse.getValeurs() != null) {
                Map<String, Double> valeurs = new HashMap<>(resultatAnalyse1.getValeurs());
                valeurs.clear(); // Supprimer les anciennes valeurs
                valeurs.putAll(resultatAnalyse.getValeurs());
                resultatAnalyse1.setValeurs(valeurs);
            }


            if (resultatAnalyse.getAnalyse() != null) resultatAnalyse1.setAnalyse(resultatAnalyse.getAnalyse());
            if (resultatAnalyse.getDetailsResultat() != null) resultatAnalyse1.setDetailsResultat(resultatAnalyse.getDetailsResultat());
        }
        em.getTransaction().commit();
    }
*/
    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        ResultatAnalyse resultatAnalyse = em.find(ResultatAnalyse.class, id);
        if (resultatAnalyse != null) {
            if (resultatAnalyse.getAnalyse() != null) {
                resultatAnalyse.setAnalyse(null);
                em.merge(resultatAnalyse);
            }
            if (resultatAnalyse.getOrdreAnalyse() != null) {
                resultatAnalyse.setOrdreAnalyse(null);
                em.merge(resultatAnalyse);
            }
            em.remove(resultatAnalyse);
        }
        em.getTransaction().commit();
    }

    @Override
    public ResultatAnalyse findById(Long id) {
        return em.find(ResultatAnalyse.class, id);
    }

    @Override
    public List<ResultatAnalyse> findAll() {
        return em.createQuery("SELECT r FROM ResultatAnalyse r", ResultatAnalyse.class).getResultList();
    }
}
