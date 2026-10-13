package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "equipement")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Equipement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipement;

    private String nom;

    // Question 21 : mappedBy pointe vers la propriété 'equipements' de Vehicule
    @ManyToMany(mappedBy = "equipements")
    private List<Vehicule> vehicules;
}