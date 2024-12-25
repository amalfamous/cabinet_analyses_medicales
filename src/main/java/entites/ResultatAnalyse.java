package entites;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

@Entity
@Data
public class ResultatAnalyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateResultat;

    private String detailsResultat;
    @ElementCollection // Spécifie une collection basique stockée dans une table associée
    @CollectionTable(name = "valeurs_resultat",
            joinColumns = @JoinColumn(name = "resultat_analyse_id")) // Définition de la table associée
    @MapKeyColumn(name = "parametre") // Colonne pour les clés du HashMap
    @Column(name = "valeur") // Colonne pour les valeurs du HashMap
    private HashMap<String, Double> valeurs;
    @ManyToOne
    @JoinColumn(name = "analyse_id")
    private Analyse analyse;
    @ManyToOne
    @JoinColumn(name = "ordre_analyse_id")
    private OrdreAnalyse ordreAnalyse;
    public ResultatAnalyse(){

    }
}
