package ma.projet.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Mariage.countEnfantsByFemmeAndDates",
                query = "SELECT SUM(m.nbrEnfant) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :startDate AND :endDate"
        )
})
public class Mariage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date dateDebut;
    private Date dateFin;
    private int nbrEnfant;

    @ManyToOne
    @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne
    @JoinColumn(name = "femme_id")
    private Femme femme;
}
