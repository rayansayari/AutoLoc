package tn.esprit.autoloc.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.repository.IEmployeRepository;

class EmployeServiceImplTest extends AbstractGenericServiceTest<Employe, Long> {

    @Mock
    private IEmployeRepository repository;

    @InjectMocks
    private EmployeServiceImpl service;

    @Override
    protected IGenericService<Employe, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Employe, Long> repository() {
        return repository;
    }

    @Override
    protected Employe entiteValide() {
        Employe employe = new Employe();
        employe.setNom("Trabelsi");
        employe.setPrenom("Sarra");
        return employe;
    }

    @Override
    protected Employe entiteInvalide() {
        Employe employe = entiteValide();
        employe.setNom("");
        return employe;
    }

    @Override
    protected Employe entiteAvecId() {
        Employe employe = entiteValide();
        employe.setIdEmploye(1L);
        return employe;
    }

    @Override
    protected Long id() {
        return 1L;
    }
}
