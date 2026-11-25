package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;
import tn.esprit.autoloc.service.IAgenceSimulationService;
import tn.esprit.autoloc.web.dto.AgenceAvecEmployesDto;
import tn.esprit.autoloc.web.dto.AgenceDto;
import tn.esprit.autoloc.web.dto.AgenceMapper;

import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService;
    private final IAgenceSimulationService simulationService;
    private final AgenceMapper agenceMapper;

    @GetMapping
    public List<AgenceDto> findAll() {
        return agenceMapper.toDtoList(agenceService.findAll());
    }

    @GetMapping("/{id}")
    public AgenceDto findById(@PathVariable Long id) {
        return agenceMapper.toDto(agenceService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgenceDto create(@Valid @RequestBody AgenceDto dto) {
        Agence agence = agenceService.create(agenceMapper.toEntity(dto));
        return agenceMapper.toDto(agence);
    }

    @PutMapping("/{id}")
    public AgenceDto update(@PathVariable Long id, @Valid @RequestBody AgenceDto dto) {
        Agence agence = agenceService.update(id, agenceMapper.toEntity(dto));
        return agenceMapper.toDto(agence);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }

    @GetMapping(value = "/{id}/simuler", produces = "text/plain;charset=UTF-8")
    public String simuler(@PathVariable Long id,
                          @RequestParam int jours,
                          @RequestParam VehiculeOffre offre) {
        Agence agence = agenceService.findById(id);
        return simulationService.simuler(agence, jours, offre);
    }

    // Mise à jour d'une agence avec ajout d'employés
    @PutMapping("/{id}/employes")
    public AgenceAvecEmployesDto ajouterEmployes(@PathVariable Long id,
                                                 @Valid @RequestBody AgenceAvecEmployesDto dto) {
        Agence agence = agenceService.ajouterEmployes(id, agenceMapper.toEntity(dto));
        return agenceMapper.toAvecEmployesDto(agence);
    }
}