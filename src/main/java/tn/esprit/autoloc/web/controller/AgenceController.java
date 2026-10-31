package tn.esprit.autoloc.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.service.IAgenceService;

import java.util.List;

@RestController                           // Question 9
@RequestMapping("/agences")               // Question 9
@RequiredArgsConstructor
public class AgenceController {

    private final IAgenceService agenceService; // Question 10

    // Question 11 : READ ALL (GET /api/agences)
    @GetMapping
    public List<Agence> findAll() {
        return agenceService.findAll();
    }

    // Question 19 : READ BY ID (GET /api/agences/{id})
    @GetMapping("/{id}")
    public Agence findById(@PathVariable Long id) {
        return agenceService.findById(id);
    }

    // Question 21 : CREATE (POST /api/agences)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retourne le code HTTP 201 CREATED
    public Agence create(@RequestBody Agence agence) {
        return agenceService.create(agence);
    }

    // Question 24 : UPDATE (PUT /api/agences/{id})
    @PutMapping("/{id}")
    public Agence update(@PathVariable Long id, @RequestBody Agence agence) {
        return agenceService.update(id, agence);
    }

    // Question 25 : DELETE (DELETE /api/agences/{id})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retourne le code HTTP 204 NO CONTENT
    public void deleteById(@PathVariable Long id) {
        agenceService.deleteById(id);
    }

    // Étape 10 : SIMULATION (GET /api/agences/{id}/simuler?jours=40&offre=LONGUE_DUREE)
    @GetMapping(value = "/{id}/simuler", produces = "text/plain;charset=UTF-8")
    public String simuler(@PathVariable Long id,
                          @RequestParam int jours,
                          @RequestParam VehiculeOffre offre) {
        Agence agence = agenceService.findById(id);
        return agenceService.simuler(agence, jours, offre);
    }
}
