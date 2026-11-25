package tn.esprit.autoloc.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = EquipementMapper.class)
public interface VehiculeMapper {

    VehiculeDto toDto(Vehicule vehicule);

    @Mapping(target = "equipements", ignore = true)
    Vehicule toEntity(VehiculeDto dto);

    List<VehiculeDto> toDtoList(List<Vehicule> vehicules);
}
