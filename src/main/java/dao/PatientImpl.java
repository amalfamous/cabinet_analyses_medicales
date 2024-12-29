package dao;

import entites.OrdreAnalyse;
import entites.Patient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class PatientImpl  implements IDao<Patient> {

    private EntityManagerFactory emf;
    private EntityManager em;

    public PatientImpl(){
        emf= Persistence.createEntityManagerFactory("cabinet");
        em=emf.createEntityManager();
    }

    @Override
    public void create(Patient patient) {
        try{
            em.getTransaction().begin();
            /*if (patient.getOrdreAnalyses() != null) {
                patient.setOrdreAnalyses(em.merge(patient.getOrdreAnalyses()));
            }*/
            if (patient.getOrdreAnalyses() != null) {
                for (OrdreAnalyse ordreAnalyse : patient.getOrdreAnalyses()) {
                    if (ordreAnalyse.getId() == 0) { // Si l'ID est 0, c'est une nouvelle entité
                        em.persist(ordreAnalyse);
                    } else {
                        em.merge(ordreAnalyse);
                    }
                }
            }
            if (patient.getUser() != null) {
                patient.setUser(em.merge(patient.getUser()));
            }
            em.persist(patient);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, Patient patient) {
        em.getTransaction().begin();
        Patient p=em.find(Patient.class,id);
        if (p!=null){
            if (patient.getNom()!=null) p.setNom(patient.getNom());
            if (patient.getPrenom()!=null) p.setPrenom(patient.getPrenom());
            if (patient.getTelephone()!=null) p.setTelephone(patient.getTelephone());
            if (patient.getEmail()!=null) p.setEmail(patient.getEmail());
            if (patient.getAdresse()!=null) p.setAdresse(patient.getAdresse());
            if (patient.getDateNaissance()!=null) p.setDateNaissance(patient.getDateNaissance());
            if (patient.getUser()!=null) p.setUser(patient.getUser());
/*
            if (patient.getOrdreAnalyses()!=null) p.setOrdreAnalyses(patient.getOrdreAnalyses());
*/
            if (patient.getOrdreAnalyses() != null) {
                for (OrdreAnalyse ordreAnalyse : patient.getOrdreAnalyses()) {
                    if (ordreAnalyse.getId() != 0) {
                        OrdreAnalyse existingOrdre = em.find(OrdreAnalyse.class, ordreAnalyse.getId());
                        if (existingOrdre != null) {
                            if (ordreAnalyse.getDateOrdre() != null) {
                                existingOrdre.setDateOrdre(ordreAnalyse.getDateOrdre());
                            }
                        }
                    }
                }
            }
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        Patient patient = em.find(Patient.class, id);
        if (patient != null) {
            if (patient.getOrdreAnalyses() != null && !patient.getOrdreAnalyses().isEmpty()) {
                for (OrdreAnalyse ordreAnalyse : patient.getOrdreAnalyses()) {
                    ordreAnalyse.setPatient(null);
                    em.merge(ordreAnalyse);
                }
            }
            if (patient.getUser() != null) {
                patient.getUser().setPatient(null);
                em.merge(patient.getUser());
            }

            em.remove(patient);
        }
        em.getTransaction().commit();
    }

    @Override
    public Patient findById(Long id) {
        return em.find(Patient.class, id);}

    @Override
    public List<Patient> findAll() {
        return em.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
    }
}
