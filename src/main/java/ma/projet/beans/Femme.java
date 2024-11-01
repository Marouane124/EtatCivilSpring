package ma.projet.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Femme.findMarriedMoreThanTwice",
                query = "SELECT f FROM Femme f JOIN f.mariages m GROUP BY f.id HAVING COUNT(m.id) >= 2"
        )
})
public class Femme extends Personne{
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Mariage> mariages;
}
