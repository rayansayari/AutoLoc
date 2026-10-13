package tn.esprit.autoloc.domain;

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

    @OneToMany(mappedBy = "agence", cascade = CascadeType.ALL)
    private List<Vehicule> vehicules;

    // Question 16 : Agence ↔ Employe (LAZY, pas de cascade suppression)
    @OneToMany(mappedBy = "agence", fetch = FetchType.LAZY)
    private List<Employe> employes;
}