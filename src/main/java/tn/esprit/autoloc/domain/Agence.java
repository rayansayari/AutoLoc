package tn.esprit.autoloc.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "agence")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Agence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgence;

    private String nom;
    private String adresse;
    private String telephone;
    private String ville;

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("agence-vehicules") // Résolution boucle Agence ↔ Vehicule
    private List<Vehicule> vehicules;

    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    @JsonManagedReference("agence-employes") // Résolution boucle Agence ↔ Employe
    private List<Employe> employes;

    // --- Alias de compatibilité ---
    public Long getId() {
        return idAgence;
    }

    public void setId(Long idAgence) {
        this.idAgence = idAgence;
    }
}