package tn.esprit.autoloc.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Maintenance;
import tn.esprit.autoloc.repository.IMaintenanceRepository;

import java.time.LocalDate;

class MaintenanceServiceImplTest extends AbstractGenericServiceTest<Maintenance, Long> {

    @Mock
    private IMaintenanceRepository repository;

    @InjectMocks
    private MaintenanceServiceImpl service;

    @Override
    protected IGenericService<Maintenance, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Maintenance, Long> repository() {
        return repository;
    }

    @Override
    protected Maintenance entiteValide() {
        Maintenance maintenance = new Maintenance();
        maintenance.setDateMaintenance(LocalDate.of(2026, 11, 1));
        maintenance.setDescription("Vidange");
        maintenance.setCout(50.0);
        return maintenance;
    }

    @Override
    protected Maintenance entiteInvalide() {
        Maintenance maintenance = entiteValide();
        maintenance.setCout(-10.0);
        return maintenance;
    }

    @Override
    protected Maintenance entiteAvecId() {
        Maintenance maintenance = entiteValide();
        maintenance.setIdMaintenance(1L);
        return maintenance;
    }

    @Override
    protected Long id() {
        return 1L;
    }
}
