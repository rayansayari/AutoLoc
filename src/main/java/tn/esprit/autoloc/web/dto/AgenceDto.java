package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AgenceDto(
        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @NotBlank(message = "L'adresse est obligatoire")
        String adresse,

        @NotBlank(message = "Le téléphone est obligatoire")
        @Pattern(regexp = "\\d{8}", message = "Le téléphone doit contenir 8 chiffres")
        String telephone,

        @NotBlank(message = "La ville est obligatoire")
        String ville
) {
}