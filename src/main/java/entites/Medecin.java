package entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.List;
@Entity
@Data
@ToString(exclude = {"ordreAnalyse"})
public class Medecin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String specialite;
    private String telephone;
    private String email;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="medecin_id")
    private List<OrdreAnalyse> ordreAnalyse;
    public Medecin(){

    }
}
