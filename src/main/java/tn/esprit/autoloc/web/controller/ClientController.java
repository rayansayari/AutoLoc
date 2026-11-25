package tn.esprit.autoloc.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.service.IClientService;
import tn.esprit.autoloc.web.dto.ClientDto;
import tn.esprit.autoloc.web.dto.ClientMapper;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final IClientService clientService;
    private final ClientMapper clientMapper;

    @GetMapping
    public List<ClientDto> findAll() {
        return clientMapper.toDtoList(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ClientDto findById(@PathVariable Long id) {
        return clientMapper.toDto(clientService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@Valid @RequestBody ClientDto dto) {
        return clientMapper.toDto(clientService.create(clientMapper.toEntity(dto)));
    }

    @PutMapping("/{id}")
    public ClientDto update(@PathVariable Long id, @Valid @RequestBody ClientDto dto) {
        return clientMapper.toDto(clientService.update(id, clientMapper.toEntity(dto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        clientService.deleteById(id);
    }
}