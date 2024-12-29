package entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@ToString(exclude = {"ordresAnalyses"})
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private LocalDateTime dateNaissance;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id")
    private List<OrdreAnalyse> ordreAnalyses;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    public Patient() {
    }

}
