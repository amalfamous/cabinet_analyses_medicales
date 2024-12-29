package dao;

import entites.OrdreAnalyse;
import entites.ResultatAnalyse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class OrdreAnalyseImpl implements IDao<OrdreAnalyse>{
    private EntityManagerFactory emf;
    private EntityManager em;

    public OrdreAnalyseImpl(){
        emf= Persistence.createEntityManagerFactory("cabinet");
        em=emf.createEntityManager();
    }

    @Override
    public void create(OrdreAnalyse ordreAnalyse) {
        try{
            em.getTransaction().begin();
            if (ordreAnalyse.getMedecin() != null) {
                ordreAnalyse.setMedecin(em.merge(ordreAnalyse.getMedecin()));
            }
            if (ordreAnalyse.getResultatAnalyses() != null) {
                ordreAnalyse.setResultatAnalyses(em.merge(ordreAnalyse.getResultatAnalyses()));
            }
            if (ordreAnalyse.getPatient() != null) {
                ordreAnalyse.setPatient(em.merge(ordreAnalyse.getPatient()));
            }
            if (ordreAnalyse.getFacture() != null) {
                ordreAnalyse.setFacture(em.merge(ordreAnalyse.getFacture()));
            }

            em.persist(ordreAnalyse);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, OrdreAnalyse ordreAnalyse) {
        em.getTransaction().begin();
        OrdreAnalyse o=em.find(OrdreAnalyse.class,id);
        if (o!=null){
            if (ordreAnalyse.getDateOrdre()!=null) o.setDateOrdre(ordreAnalyse.getDateOrdre());
            if (ordreAnalyse.getResultatAnalyses()!=null) o.setResultatAnalyses(ordreAnalyse.getResultatAnalyses());
            if (ordreAnalyse.getFacture()!=null) o.setFacture(ordreAnalyse.getFacture());
            if (ordreAnalyse.getPatient()!=null) o.setPatient(ordreAnalyse.getPatient());
            if (ordreAnalyse.getMedecin()!=null) o.setMedecin(ordreAnalyse.getMedecin());
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        OrdreAnalyse ordreAnalyse = em.find(OrdreAnalyse.class, id);
        if (ordreAnalyse != null) {
            if (ordreAnalyse.getResultatAnalyses() != null && !ordreAnalyse.getResultatAnalyses().isEmpty()) {
                for (ResultatAnalyse resultatAnalyse : ordreAnalyse.getResultatAnalyses()) {
                    resultatAnalyse.setOrdreAnalyse(null);
                    em.merge(resultatAnalyse);
                }
            }

            if (ordreAnalyse.getFacture() != null) {
                ordreAnalyse.setFacture(null);
                em.merge(ordreAnalyse);
            }
            if (ordreAnalyse.getPatient() != null) {
                ordreAnalyse.setPatient(null);
                em.merge(ordreAnalyse);
            }
            if (ordreAnalyse.getMedecin() != null) {
                ordreAnalyse.setMedecin(null);
                em.merge(ordreAnalyse);
            }
            em.remove(ordreAnalyse);
        }
        em.getTransaction().commit();
    }

    @Override
    public OrdreAnalyse findById(Long id) {
        return em.find(OrdreAnalyse.class, id);
    }

    @Override
    public List<OrdreAnalyse> findAll() {
        return em.createQuery("SELECT o FROM OrdreAnalyse o", OrdreAnalyse.class).getResultList();
    }
}
