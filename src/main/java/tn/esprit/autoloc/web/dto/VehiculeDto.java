package tn.esprit.autoloc.web.dto;

import java.util.List;

public record VehiculeDto(
        Long idVehicule,
        String immatriculation,
        String marque,
        String modele,
        String categorie,
        String statut,
        Double tarifJournalier,
        List<EquipementDto> equipements
) {
}