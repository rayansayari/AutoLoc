package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;

public record EquipementDto(
        Long idEquipement,

        @NotBlank(message = "Le nom de l'équipement est obligatoire")
        String nom
) {
}