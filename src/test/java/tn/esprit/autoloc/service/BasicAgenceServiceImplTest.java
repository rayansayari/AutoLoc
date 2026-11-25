package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class BasicAgenceServiceImplTest extends AbstractGenericServiceTest<Agence, Long> {

    @Mock
    private IAgenceRepository repository;

    @InjectMocks
    private BasicAgenceServiceImpl service;

    @Override
    protected IGenericService<Agence, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Agence, Long> repository() {
        return repository;
    }

    @Override
    protected Agence entiteValide() {
        Agence agence = new Agence();
        agence.setNom("Agence Ariana");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");
        return agence;
    }

    @Override
    protected Agence entiteInvalide() {
        Agence agence = entiteValide();
        agence.setNom("");
        return agence;
    }

    @Override
    protected Agence entiteAvecId() {
        Agence agence = entiteValide();
        agence.setIdAgence(1L);
        return agence;
    }

    @Override
    protected Long id() {
        return 1L;
    }

    @Test
    void hasVehiculesRetourneVraiSiLAgenceADesVehicules() {
        Agence agence = entiteAvecId();
        agence.setVehicules(List.of(new Vehicule()));
        when(repository.findById(1L)).thenReturn(Optional.of(agence));

        assertTrue(service.hasVehicules(1L));
    }

    @Test
    void hasVehiculesRetourneFauxSiLAgenceNEnAPas() {
        Agence agence = entiteAvecId();
        agence.setVehicules(List.of());
        when(repository.findById(1L)).thenReturn(Optional.of(agence));

        assertFalse(service.hasVehicules(1L));
    }
}
