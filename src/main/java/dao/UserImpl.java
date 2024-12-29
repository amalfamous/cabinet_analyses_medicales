package dao;

import entites.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.mindrot.jbcrypt.BCrypt;
import java.util.List;

public class UserImpl implements IDao<User> {
    private EntityManagerFactory emf;
    private EntityManager em;

    public UserImpl() {
        emf = Persistence.createEntityManagerFactory("cabinet");
        em = emf.createEntityManager();
    }

    @Override
    public void create(User user) {
        try {
            em.getTransaction().begin();
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
            if (user.getLaborantin() != null) {
                user.setLaborantin(em.merge(user.getLaborantin()));
            }
            if (user.getPatient() != null) {
                user.setPatient(em.merge(user.getPatient()));
            }
            if (user.getMedecin() != null) {
                user.setMedecin(em.merge(user.getMedecin()));
            }
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Long id, User user) {
        em.getTransaction().begin();
        User  u = em.find(User.class, id);
        if ( u != null) {
            if (user.getUsername() != null)  u.setUsername(user.getUsername());
            if (user.getPassword() != null) {//Si le mot de passe est modifié, il doit être hashé à nouveau avant d'être mis à jour
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
                u.setPassword(hashedPassword);
            }
           // if (user.getPassword() != null)  u.setPassword(user.getPassword()); //=> si le mot de passe est changé, il sera stocké en clair.
            if (user.getRole() != null)  u.setRole(user.getRole());
            if (user.getMedecin() != null)  u.setMedecin(user.getMedecin());
            if (user.getPatient() != null)  u.setPatient(user.getPatient());
            if (user.getLaborantin() != null)  u.setLaborantin(user.getLaborantin());
        }
        em.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            if (user.getLaborantin() != null) {
                user.getLaborantin().setUser(null);
                em.merge(user.getLaborantin());
            }
            if (user.getMedecin() != null) {
                user.getMedecin().setUser(null);
                em.merge(user.getMedecin());
            }
            if (user.getPatient() != null) {
                user.getPatient().setUser(null);
                em.merge(user.getPatient());
            }

            em.remove(user);
        }
        em.getTransaction().commit();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


    public User authenticate(String username, String password) {
        try {
            // Trouver l'utilisateur avec le nom d'utilisateur
            User user = em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();

            // Vérifier si le mot de passe correspond
            if (user != null && BCrypt.checkpw(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

}
