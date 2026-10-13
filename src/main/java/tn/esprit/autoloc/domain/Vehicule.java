package tn.esprit.autoloc.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "vehicule")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehicule;

    @Column(nullable = false, unique = true, length = 20)
    private String immatriculation;

    private String marque;
    private String modele;
    private String categorie;
    private String statut;
    private Double tarifJournalier;

    @ManyToOne
    private Agence agence;

    // Question 16 : Relation Vehicule <-> Reservation
    @OneToMany(mappedBy = "vehicule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    // Question 19 : Relation Vehicule <-> Maintenance
    @OneToMany(mappedBy = "vehicule")
    private List<Maintenance> maintenances;

    // Question 21 : Relation ManyToMany avec Equipement (L'ATTRIBUT MANQUANT ÉTAIT ICI)
    @ManyToMany
    private List<Equipement> equipements;
}