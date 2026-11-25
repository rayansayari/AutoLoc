package tn.esprit.autoloc.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.calcul.LongueDureeOffre;
import tn.esprit.autoloc.calcul.OffreStrategy;
import tn.esprit.autoloc.calcul.StandardOffre;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.calcul.WeekendOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Equipement;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;
import tn.esprit.autoloc.repository.IEquipementRepository;
import tn.esprit.autoloc.repository.IVehiculeRepository;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class VehiculeServiceImplTest extends AbstractGenericServiceTest<Vehicule, Long> {

    @Mock
    private IVehiculeRepository vehiculeRepository;

    @Mock
    private IAgenceRepository agenceRepository;

    @Mock
    private IEquipementRepository equipementRepository;

    private VehiculeServiceImpl service;

    @BeforeEach
    void init() {
        Map<String, OffreStrategy> strategies = Map.of(
                VehiculeOffre.STANDARD.getNomBean(), new StandardOffre(),
                VehiculeOffre.WEEKEND.getNomBean(), new WeekendOffre(),
                VehiculeOffre.LONGUE_DUREE.getNomBean(), new LongueDureeOffre());
        service = new VehiculeServiceImpl(vehiculeRepository, agenceRepository, equipementRepository, strategies);
    }

    @Override
    protected IGenericService<Vehicule, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Vehicule, Long> repository() {
        return vehiculeRepository;
    }

    @Override
    protected Vehicule entiteValide() {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("1111TU1");
        vehicule.setMarque("Isuzu");
        vehicule.setModele("DMax");
        vehicule.setTarifJournalier(100.0);
        return vehicule;
    }

    @Override
    protected Vehicule entiteInvalide() {
        Vehicule vehicule = entiteValide();
        vehicule.setTarifJournalier(-5.0);
        return vehicule;
    }

    @Override
    protected Vehicule entiteAvecId() {
        Vehicule vehicule = entiteValide();
        vehicule.setIdVehicule(1L);
        return vehicule;
    }

    @Override
    protected Long id() {
        return 1L;
    }

    @Test
    void calculTarifStandard() {
        assertEquals(500.0, service.calculTarif(entiteValide(), 5, VehiculeOffre.STANDARD), 0.001);
    }

    @Test
    void calculTarifWeekendApplique20PourCentDeReduction() {
        assertEquals(400.0, service.calculTarif(entiteValide(), 5, VehiculeOffre.WEEKEND), 0.001);
    }

    @Test
    void calculTarifLongueDureeApplique25PourCentDeReduction() {
        assertEquals(375.0, service.calculTarif(entiteValide(), 5, VehiculeOffre.LONGUE_DUREE), 0.001);
    }

    @Test
    void calculTarifLongueDureeAjoute5PourCentAuDelaDe2000() {
        // 100 * 0,75 * 40 = 3000, puis -5 % = 2850
        assertEquals(2850.0, service.calculTarif(entiteValide(), 40, VehiculeOffre.LONGUE_DUREE), 0.001);
    }

    @Test
    void desaffecterAgenceMetLAgenceANull() {
        Vehicule vehicule = entiteAvecId();
        vehicule.setAgence(new Agence());
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(vehicule));
        when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(returnsFirstArg());

        Vehicule resultat = service.desaffecterAgence(1L);

        assertNull(resultat.getAgence());
    }

    @Test
    void affecterAgenceAssocieLAgence() {
        Agence agence = new Agence();
        agence.setIdAgence(5L);
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(entiteAvecId()));
        when(agenceRepository.findById(5L)).thenReturn(Optional.of(agence));
        when(vehiculeRepository.save(any(Vehicule.class))).thenAnswer(returnsFirstArg());

        Vehicule resultat = service.affecterAgence(1L, 5L);

        assertSame(agence, resultat.getAgence());
    }

    @Test
    void affecterAgenceLeveUneExceptionSiLAgenceNExistePas() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(entiteAvecId()));
        when(agenceRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.affecterAgence(1L, 9L));
        verify(vehiculeRepository, never()).save(any());
    }

    @Test
    void affecterEquipementAjouteLEquipementALaVehicule() {
        Equipement equipement = new Equipement();
        equipement.setIdEquipement(3L);
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(entiteAvecId()));
        when(equipementRepository.findById(3L)).thenReturn(Optional.of(equipement));

        Vehicule resultat = service.affecterEquipement(1L, 3L);

        assertEquals(1, resultat.getEquipements().size());
        assertSame(equipement, resultat.getEquipements().get(0));
    }

    @Test
    void affecterEquipementLeveUneExceptionSiLEquipementNExistePas() {
        when(vehiculeRepository.findById(1L)).thenReturn(Optional.of(entiteAvecId()));
        when(equipementRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.affecterEquipement(1L, 9L));
    }
}
