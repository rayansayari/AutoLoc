package tn.esprit.autoloc.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.domain.Agence;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AgenceMapper {

    AgenceDto toDto(Agence agence);

    Agence toEntity(AgenceDto dto);

    List<AgenceDto> toDtoList(List<Agence> agences);

    List<Agence> toEntityList(List<AgenceDto> dtos);

    AgenceAvecEmployesDto toAvecEmployesDto(Agence agence);

    Agence toEntity(AgenceAvecEmployesDto dto);
}