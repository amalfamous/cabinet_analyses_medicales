package entites;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDateTime;
@Entity
@Data
@ToString(exclude = {"ordreAnalyse"})
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Double montantTotal;
    private LocalDateTime dateFacture;
    private boolean etatPaiement; // payé/non payé
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordre_analyse_id")
    private OrdreAnalyse ordreAnalyse;
    public Facture(){

    }
}
