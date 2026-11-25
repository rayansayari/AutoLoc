package tn.esprit.autoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IEquipementService;
import tn.esprit.autoloc.web.dto.EquipementDto;
import tn.esprit.autoloc.web.dto.EquipementMapper;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
public class EquipementController {

    private final IEquipementService equipementService;
    private final EquipementMapper equipementMapper;

    @GetMapping
    public List<EquipementDto> findAll() {
        return equipementMapper.toDtoList(equipementService.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EquipementDto create(@RequestBody EquipementDto dto) {
        return equipementMapper.toDto(equipementService.create(equipementMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        equipementService.deleteById(id);
    }
}