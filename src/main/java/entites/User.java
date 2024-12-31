package entites;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    private String role; 

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id" )
    private Patient patient;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laborantin_id" )
    private Laborantin laborantin;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "medecin_id" )
    private Medecin medecin;
    public User() {
    }
}
