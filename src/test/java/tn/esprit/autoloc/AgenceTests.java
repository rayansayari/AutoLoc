package tn.esprit.autoloc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Vehicule;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AgenceTests {

    @Autowired
    private AgenceRepositoryMock agenceRepository;

    @Test
    public void addAgence() {
        Agence agence = new Agence();
        agence.setNom("Agence ariana");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");

        Vehicule v1 = new Vehicule();
        v1.setCategorie("SUV");
        // Modification légère des immatriculations pour éviter l'erreur de doublon
        v1.setImmatriculation("785414TU99");
        v1.setMarque("Isuzu");
        v1.setModele("DMax");
        v1.setStatut("MAINTENANCE");
        v1.setTarifJournalier(100.0);
        v1.setAgence(agence);

        Vehicule v2 = new Vehicule();
        v2.setCategorie("UTILITAIRE");
        v2.setImmatriculation("785414TU98");
        v2.setMarque("Toyota");
        v2.setModele("Yaris");
        v2.setStatut("DISPONIBLE");
        v2.setTarifJournalier(80.0);
        v2.setAgence(agence);

        List<Vehicule> vehicules = new ArrayList<>();
        vehicules.add(v1);
        vehicules.add(v2);
        agence.setVehicules(vehicules);

        agenceRepository.save(agence);
    }

    // @Transactional permet d'éviter la LazyInitializationException
    @Test
    @Transactional
    public void loadAgence() {
        Iterable<Agence> agences = agenceRepository.findAll();
        StringBuilder sb = new StringBuilder();

        for (Agence agence : agences) {
            sb.append("\n")
                    .append(agence.getIdAgence()).append(" | ").append(agence.getNom()).append("\n");

            int count = (agence.getVehicules() != null) ? agence.getVehicules().size() : 0;
            sb.append("Vehicules Count : ").append(count).append("\n");

            if (agence.getVehicules() != null) {
                for (Vehicule v : agence.getVehicules()) {
                    sb.append("=== ")
                            .append(v.getIdVehicule()).append("|")
                            .append(v.getImmatriculation()).append("\n");
                }
            }
        }

        Assert.fail(sb.toString());
    }
}

interface AgenceRepositoryMock extends CrudRepository<Agence, Long> {
}