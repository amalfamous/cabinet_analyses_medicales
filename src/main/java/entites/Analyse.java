package entites;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@ToString(exclude = {"laborantin","resultatAnalyses"})
public class Analyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private Double prix;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "analyse_id")
    private List<ResultatAnalyse> resultatAnalyses;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laborantin_id")
    private Laborantin laborantin;
    public Analyse(){

    }
}
