package tn.esprit.autoloc.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Client;
import tn.esprit.autoloc.repository.IClientRepository;

class ClientServiceImplTest extends AbstractGenericServiceTest<Client, Long> {

    @Mock
    private IClientRepository repository;

    @InjectMocks
    private ClientServiceImpl service;

    @Override
    protected IGenericService<Client, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Client, Long> repository() {
        return repository;
    }

    @Override
    protected Client entiteValide() {
        Client client = new Client();
        client.setNom("Ben Salah");
        client.setPrenom("Ali");
        return client;
    }

    @Override
    protected Client entiteInvalide() {
        Client client = entiteValide();
        client.setNom("");
        return client;
    }

    @Override
    protected Client entiteAvecId() {
        Client client = entiteValide();
        client.setIdClient(1L);
        return client;
    }

    @Override
    protected Long id() {
        return 1L;
    }
}
