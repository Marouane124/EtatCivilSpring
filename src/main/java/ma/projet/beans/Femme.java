package ma.projet.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Femme extends Personne{
    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;
}
