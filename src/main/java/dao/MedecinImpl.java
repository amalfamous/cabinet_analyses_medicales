package dao;

import entites.Medecin;
import entites.OrdreAnalyse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class MedecinImpl  implements IDao<Medecin> {

    private EntityManagerFactory emf;
    private EntityManager em;

    public MedecinImpl(){
        emf= Persistence.createEntityManagerFactory("cabinet");
        em=emf.createEntityManager();
    }

    @Override
    public void create(Medecin medecin) {
        try{
            em.getTransaction().begin();
            if (medecin.getOrdreAnalyse() != null) {
                medecin.setOrdreAnalyse(em.merge(medecin.getOrdreAnalyse()));
            }
            em.persist(medecin);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, Medecin medecin) {
        em.getTransaction().begin();
        Medecin l=em.find(Medecin.class,id);
        if (l!=null){
            if (medecin.getNom()!=null) l.setNom(medecin.getNom());
            if (medecin.getPrenom()!=null) l.setPrenom(medecin.getPrenom());
            if (medecin.getTelephone()!=null) l.setTelephone(medecin.getTelephone());
            if (medecin.getEmail()!=null) l.setEmail(medecin.getEmail());
            if (medecin.getSpecialite()!=null) l.setSpecialite(medecin.getSpecialite());
            if (medecin.getOrdreAnalyse()!=null) l.setOrdreAnalyse(medecin.getOrdreAnalyse());
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Medecin medecin = em.find(Medecin.class, id);
        if (medecin != null) {
            if (medecin.getOrdreAnalyse() != null && !medecin.getOrdreAnalyse().isEmpty()) {
                for (OrdreAnalyse ordreAnalyse : medecin.getOrdreAnalyse()) {
                    ordreAnalyse.setMedecin(null);
                    em.merge(ordreAnalyse);
                }
            }
            em.remove(medecin);
        }
        em.getTransaction().commit();
    }

    @Override
    public Medecin findById(Long id) {
        return em.find(Medecin.class, id);
    }

    @Override
    public List<Medecin> findAll() {
        return em.createQuery("SELECT m FROM Medecin m", Medecin.class).getResultList();
    }
}
