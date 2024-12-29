package entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Entity
@Data
@ToString(exclude = {"analyse","ordreAnalyse"})
public class ResultatAnalyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateResultat;

    private String detailsResultat;
    @ElementCollection
    @CollectionTable(name = "resultat_analyse_valeurs",
            joinColumns = @JoinColumn(name = "resultat_analyse_id"))
    @MapKeyColumn(name = "valeur_key")
    @Column(name = "valeur_value")
    private Map<String, Double> valeurs=new HashMap<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "analyse_id")
    private Analyse analyse;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordre_analyse_id")
    private OrdreAnalyse ordreAnalyse;
    public ResultatAnalyse() {
        this.valeurs = new HashMap<>();
        this.valeurs.put("parametre", 0.0);
    }

}
