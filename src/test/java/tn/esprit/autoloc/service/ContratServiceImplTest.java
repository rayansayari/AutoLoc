package tn.esprit.autoloc.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Contrat;
import tn.esprit.autoloc.repository.IContratRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

class ContratServiceImplTest extends AbstractGenericServiceTest<Contrat, Long> {

    @Mock
    private IContratRepository repository;

    @InjectMocks
    private ContratServiceImpl service;

    @Override
    protected IGenericService<Contrat, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Contrat, Long> repository() {
        return repository;
    }

    @Override
    protected Contrat entiteValide() {
        Contrat contrat = new Contrat();
        contrat.setDateSignature(LocalDate.of(2026, 11, 1));
        contrat.setMontantTotal(new BigDecimal("500.00"));
        return contrat;
    }

    @Override
    protected Contrat entiteInvalide() {
        Contrat contrat = entiteValide();
        contrat.setMontantTotal(null);
        return contrat;
    }

    @Override
    protected Contrat entiteAvecId() {
        Contrat contrat = entiteValide();
        contrat.setIdContrat(1L);
        return contrat;
    }

    @Override
    protected Long id() {
        return 1L;
    }
}
