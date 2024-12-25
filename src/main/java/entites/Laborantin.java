package entites;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Laborantin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    @OneToMany
    @JoinColumn(name="laborantin_id")
    private List<Analyse> analyses;
    public Laborantin(){

    }
}
