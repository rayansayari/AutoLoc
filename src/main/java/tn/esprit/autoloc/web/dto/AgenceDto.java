package tn.esprit.autoloc.web.dto;

public record AgenceDto(
        String nom,
        String adresse,
        String telephone,
        String ville
) {
}