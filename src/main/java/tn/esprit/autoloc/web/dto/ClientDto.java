package tn.esprit.autoloc.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

public record ClientDto(
        Long idClient,

        @NotBlank(message = "Le nom est obligatoire")
        String nom,

        @NotBlank(message = "Le prénom est obligatoire")
        String prenom,

        @NotBlank(message = "L'email est obligatoire")
        @Email(message = "L'email n'est pas valide")
        String email,

        @Pattern(regexp = "\\d{8}", message = "Le téléphone doit contenir 8 chiffres")
        String telephone,

        @NotBlank(message = "Le numéro de permis est obligatoire")
        String numPermis,

        @PastOrPresent(message = "La date d'inscription ne peut pas être dans le futur")
        LocalDate dateInscription
) {
}
