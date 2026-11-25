package tn.esprit.autoloc.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.domain.Equipement;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EquipementMapper {

    EquipementDto toDto(Equipement equipement);

    Equipement toEntity(EquipementDto dto);

    List<EquipementDto> toDtoList(List<Equipement> equipements);
}
