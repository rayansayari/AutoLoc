package tn.esprit.autoloc.web.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import tn.esprit.autoloc.domain.Client;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientDto toDto(Client client);

    Client toEntity(ClientDto dto);

    List<ClientDto> toDtoList(List<Client> clients);
}