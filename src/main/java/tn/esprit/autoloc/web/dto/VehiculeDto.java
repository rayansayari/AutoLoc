package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record VehiculeDto(
        Long idVehicule,

        @NotBlank(message = "L'immatriculation est obligatoire")
        @Size(max = 20, message = "L'immatriculation ne doit pas dépasser 20 caractères")
        String immatriculation,

        @NotBlank(message = "La marque est obligatoire")
        String marque,

        @NotBlank(message = "Le modèle est obligatoire")
        String modele,

        @NotBlank(message = "La catégorie est obligatoire")
        String categorie,

        @NotBlank(message = "Le statut est obligatoire")
        String statut,

        @NotNull(message = "Le tarif journalier est obligatoire")
        @Positive(message = "Le tarif journalier doit être strictement positif")
        Double tarifJournalier,

        List<EquipementDto> equipements
) {
}