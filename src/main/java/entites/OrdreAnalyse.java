package entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString(exclude = {"patient", "medecin", "facture","resultatAnalyses"})
public class OrdreAnalyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateOrdre;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordre_analyse_id")
    private List<ResultatAnalyse> resultatAnalyses;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "facture_id")
    private Facture facture;
    public OrdreAnalyse(){
        this.resultatAnalyses = new ArrayList<>();
    }
}
