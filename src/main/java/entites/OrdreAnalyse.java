package entites;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class OrdreAnalyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateOrdre;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;
    @OneToMany
    @JoinColumn(name = "ordre_analyse_id")
    private List<ResultatAnalyse> resultatAnalyses;
    @OneToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;
    public OrdreAnalyse(){

    }
}
