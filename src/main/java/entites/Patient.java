package entites;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
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
    @OneToMany
    @JoinColumn(name="patient_id")
    private List<OrdreAnalyse> ordreAnalyses;

    public Patient() {
    }
}
