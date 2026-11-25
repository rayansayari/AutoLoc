package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IVehiculeService;
import tn.esprit.autoloc.web.dto.VehiculeDto;
import tn.esprit.autoloc.web.dto.VehiculeMapper;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
public class VehiculeController {

    private final IVehiculeService vehiculeService;
    private final VehiculeMapper vehiculeMapper;

    @GetMapping
    public List<VehiculeDto> findAll() {
        return vehiculeMapper.toDtoList(vehiculeService.findAll());
    }

    @GetMapping("/{id}")
    public VehiculeDto findById(@PathVariable Long id) {
        return vehiculeMapper.toDto(vehiculeService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VehiculeDto create(@Valid @RequestBody VehiculeDto dto) {
        return vehiculeMapper.toDto(vehiculeService.create(vehiculeMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public VehiculeDto update(@PathVariable Long id, @Valid @RequestBody VehiculeDto dto) {
        return vehiculeMapper.toDto(vehiculeService.update(id, vehiculeMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        vehiculeService.deleteById(id);
    }

    @PutMapping("/{id}/desaffecter")
    public VehiculeDto desaffecter(@PathVariable Long id) {
        return vehiculeMapper.toDto(vehiculeService.desaffecterAgence(id));
    }

    @PutMapping("/{idVehicule}/affecter/{idAgence}")
    public VehiculeDto affecter(@PathVariable Long idVehicule, @PathVariable Long idAgence) {
        return vehiculeMapper.toDto(vehiculeService.affecterAgence(idVehicule, idAgence));
    }

    @PutMapping("/{idVehicule}/equipements/{idEquipement}")
    public VehiculeDto affecterEquipement(@PathVariable Long idVehicule, @PathVariable Long idEquipement) {
        return vehiculeMapper.toDto(vehiculeService.affecterEquipement(idVehicule, idEquipement));
    }
}