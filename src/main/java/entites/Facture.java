package entites;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double montantTotal;
    private LocalDateTime dateFacture;
    private boolean etatPaiement; // payé/non payé
    @OneToOne
    @JoinColumn(name = "ordre_analyse_id")
    private OrdreAnalyse ordreAnalyse;
    public Facture(){

    }
}
