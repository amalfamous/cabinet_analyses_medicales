package entites;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Analyse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String description;
    private Double prix;
    @OneToMany
    @JoinColumn(name = "analyse_id")
    private List<ResultatAnalyse> resultatAnalyses;
    @ManyToOne
    @JoinColumn(name = "laborantin_id")
    private Laborantin laborantin;
    public Analyse(){

    }
}
