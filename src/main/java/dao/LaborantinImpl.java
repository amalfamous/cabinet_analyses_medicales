package dao;

import entites.Analyse;
import entites.Laborantin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class LaborantinImpl implements IDao<Laborantin> {

    private EntityManagerFactory emf;
    private EntityManager em;

    public LaborantinImpl(){
        emf= Persistence.createEntityManagerFactory("cabinet");
        em=emf.createEntityManager();
    }

    @Override
    public void create(Laborantin laborantin) {
        try{
            em.getTransaction().begin();
            if (laborantin.getAnalyses() != null) {
                laborantin.setAnalyses(em.merge(laborantin.getAnalyses()));
            }
            if (laborantin.getUser() != null) {
                laborantin.setUser(em.merge(laborantin.getUser()));
            }
            em.persist(laborantin);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, Laborantin laborantin) {
        em.getTransaction().begin();
        Laborantin l=em.find(Laborantin.class,id);
        if (l!=null){
            if (laborantin.getNom()!=null) l.setNom(laborantin.getNom());
            if (laborantin.getPrenom()!=null) l.setPrenom(laborantin.getPrenom());
            if (laborantin.getTelephone()!=null) l.setTelephone(laborantin.getTelephone());
            if (laborantin.getEmail()!=null) l.setEmail(laborantin.getEmail());
            if (laborantin.getAnalyses()!=null) l.setAnalyses(laborantin.getAnalyses());
            if (laborantin.getUser() != null) l.setUser(em.merge(laborantin.getUser()));

        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Laborantin laborantin = em.find(Laborantin.class, id);
        if (laborantin != null) {
            if (laborantin.getAnalyses() != null && !laborantin.getAnalyses().isEmpty()) {
                for (Analyse analyse : laborantin.getAnalyses()) {
                    analyse.setLaborantin(null);
                    em.merge(analyse);
                }
            }
            if (laborantin.getUser() != null) {
                laborantin.getUser().setPatient(null);
                em.merge(laborantin.getUser());
            }
            em.remove(laborantin);
        }
        em.getTransaction().commit();

    }

    @Override
    public Laborantin findById(Long id) {
        return em.find(Laborantin.class, id);
    }

    @Override
    public List<Laborantin> findAll() {
        return em.createQuery("SELECT l FROM Laborantin l", Laborantin.class).getResultList();
    }
}
