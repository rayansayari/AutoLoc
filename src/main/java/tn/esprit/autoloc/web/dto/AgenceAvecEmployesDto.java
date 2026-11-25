package tn.esprit.autoloc.web.dto;

import java.util.List;

public record AgenceAvecEmployesDto(
        String nom,
        String adresse,
        String telephone,
        String ville,
        int effectif,
        List<EmployeDto> employes
) {
}
