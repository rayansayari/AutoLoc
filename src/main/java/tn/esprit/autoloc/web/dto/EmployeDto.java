package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.NotBlank;

public record EmployeDto(
        Long idEmploye,

        @NotBlank(message = "Le nom de l'employé est obligatoire")
        String nom,

        @NotBlank(message = "Le prénom de l'employé est obligatoire")
        String prenom
) {
}