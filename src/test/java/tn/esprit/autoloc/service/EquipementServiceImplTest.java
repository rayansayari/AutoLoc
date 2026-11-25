package tn.esprit.autoloc.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.repository.IEquipementRepository;

class EquipementServiceImplTest extends AbstractGenericServiceTest<Equipement, Long> {

    @Mock
    private IEquipementRepository repository;

    @InjectMocks
    private EquipementServiceImpl service;

    @Override
    protected IGenericService<Equipement, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Equipement, Long> repository() {
        return repository;
    }

    @Override
    protected Equipement entiteValide() {
        Equipement equipement = new Equipement();
        equipement.setNom("GPS");
        return equipement;
    }

    @Override
    protected Equipement entiteInvalide() {
        Equipement equipement = new Equipement();
        equipement.setNom("");
        return equipement;
    }

    @Override
    protected Equipement entiteAvecId() {
        Equipement equipement = entiteValide();
        equipement.setIdEquipement(1L);
        return equipement;
    }

    @Override
    protected Long id() {
        return 1L;
    }
}
