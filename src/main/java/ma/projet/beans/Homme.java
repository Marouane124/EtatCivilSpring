package ma.projet.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Homme extends Personne{
    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Mariage> mariages;
}
