package ma.projet.beans;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "femmes")
@NamedQueries({
        @NamedQuery(name = "Femme.marriedAtLeastTwice", query = "SELECT f FROM Femme f WHERE size(f.mariages) >= 2")
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Femme.countEnfantsBetweenDates",
                query = "SELECT COALESCE(SUM(m.nb_enfants),0) AS total FROM mariages m WHERE m.femme_id = :femmeId AND m.date_debut BETWEEN :d1 AND :d2",
                resultSetMapping = "Mapping.TotalLong"
        )
})
@SqlResultSetMapping(
        name = "Mapping.TotalLong",
        columns = @ColumnResult(name = "total", type = Long.class)
)
public class Femme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;

    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mariage> mariages = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public Date getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(Date dateNaissance) { this.dateNaissance = dateNaissance; }
    public List<Mariage> getMariages() { return mariages; }
    public void setMariages(List<Mariage> mariages) { this.mariages = mariages; }
}
