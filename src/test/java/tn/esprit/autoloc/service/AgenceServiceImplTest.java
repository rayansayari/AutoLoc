package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.calcul.VehiculeOffre;
import tn.esprit.autoloc.domain.Agence;
import tn.esprit.autoloc.domain.Employe;
import tn.esprit.autoloc.domain.Vehicule;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IAgenceRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgenceServiceImplTest {

    @Mock
    private IAgenceRepository agenceRepository;

    @Mock
    private IVehiculeService vehiculeService;

    @Mock
    private IEmployeService employeService;

    @InjectMocks
    private AgenceServiceImpl service;

    private Agence agenceValide() {
        Agence agence = new Agence();
        agence.setNom("Agence Ariana");
        agence.setAdresse("1 Rue Hedi");
        agence.setTelephone("71585874");
        agence.setVille("Tunis");
        return agence;
    }

    private Employe employe(String nom, String prenom) {
        Employe employe = new Employe();
        employe.setNom(nom);
        employe.setPrenom(prenom);
        return employe;
    }

    @Test
    void createIgnoreLIdentifiantFourni() {
        Agence agence = agenceValide();
        agence.setIdAgence(99L);
        when(agenceRepository.save(any(Agence.class))).thenAnswer(returnsFirstArg());

        Agence resultat = service.create(agence);

        assertNull(resultat.getIdAgence());
        verify(agenceRepository).save(agence);
    }

    @Test
    void createRefuseUneAgenceInvalide() {
        Agence agence = agenceValide();
        agence.setNom("");

        assertThrows(IllegalArgumentException.class, () -> service.create(agence));
        verify(agenceRepository, never()).save(any());
    }

    @Test
    void updateMetAJourLesChamps() {
        Agence existante = agenceValide();
        existante.setIdAgence(1L);
        Agence nouvelle = agenceValide();
        nouvelle.setNom("Agence Sfax");
        nouvelle.setVille("Sfax");
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(existante));
        when(agenceRepository.save(any(Agence.class))).thenAnswer(returnsFirstArg());

        Agence resultat = service.update(1L, nouvelle);

        assertEquals("Agence Sfax", resultat.getNom());
        assertEquals("Sfax", resultat.getVille());
    }

    @Test
    void updateRefuseNull() {
        assertThrows(IllegalArgumentException.class, () -> service.update(1L, null));
    }

    @Test
    void updateRefuseUneAgenceInvalide() {
        Agence invalide = agenceValide();
        invalide.setAdresse(" ");

        assertThrows(IllegalArgumentException.class, () -> service.update(1L, invalide));
        verify(agenceRepository, never()).save(any());
    }

    @Test
    void updateLeveUneExceptionSiInexistante() {
        Agence agence = agenceValide();
        when(agenceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(1L, agence));
    }

    @Test
    void deleteByIdSupprimeLAgence() {
        Agence agence = agenceValide();
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(agence));

        assertDoesNotThrow(() -> service.deleteById(1L));
        verify(agenceRepository).delete(agence);
    }

    @Test
    void deleteByIdLeveUneExceptionSiInexistante() {
        when(agenceRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(1L));
        verify(agenceRepository, never()).delete(any());
    }

    @Test
    void checkRefuseNull() {
        assertFalse(service.check(null, true));
    }

    @Test
    void checkAccepteUneAgenceValide() {
        assertTrue(service.check(agenceValide(), true));
    }

    @Test
    void checkRefuseUneVilleVide() {
        Agence agence = agenceValide();
        agence.setVille("  ");

        assertFalse(service.check(agence, true));
    }

    @Test
    void hasVehiculesRetourneVraiSiLAgenceADesVehicules() {
        Agence agence = agenceValide();
        agence.setVehicules(List.of(new Vehicule()));
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(agence));

        assertTrue(service.hasVehicules(1L));
    }

    @Test
    void hasVehiculesRetourneFauxSiLaListeEstVide() {
        Agence agence = agenceValide();
        agence.setVehicules(new ArrayList<>());
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(agence));

        assertFalse(service.hasVehicules(1L));
    }

    @Test
    void simulerAdditionneLesTarifsDesVehicules() {
        Vehicule v1 = new Vehicule();
        Vehicule v2 = new Vehicule();
        Agence agence = agenceValide();
        agence.setIdAgence(1L);
        agence.setVehicules(List.of(v1, v2));
        when(vehiculeService.calculTarif(v1, 5, VehiculeOffre.STANDARD)).thenReturn(500.0);
        when(vehiculeService.calculTarif(v2, 5, VehiculeOffre.STANDARD)).thenReturn(750.0);

        String rapport = service.simuler(agence, 5, VehiculeOffre.STANDARD);

        assertTrue(rapport.contains("Agence Ariana"));
        assertTrue(rapport.contains("1250.0"));
    }

    @Test
    void ajouterEmployesInsereLesEmployesEtMetAJourLEffectif() {
        Agence existante = agenceValide();
        existante.setIdAgence(1L);
        Employe e1 = employe("Ben Salah", "Ali");
        Employe e2 = employe("Trabelsi", "Sarra");
        Agence demande = agenceValide();
        demande.setEmployes(new ArrayList<>(List.of(e1, e2)));
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(existante));
        when(agenceRepository.save(any(Agence.class))).thenAnswer(returnsFirstArg());

        Agence resultat = service.ajouterEmployes(1L, demande);

        assertEquals(2, resultat.getEffectif());
        assertEquals(2, resultat.getEmployes().size());
        assertSame(resultat, e1.getAgence());
        verify(employeService, times(2)).create(any(Employe.class));
    }

    @Test
    void ajouterEmployesSansEmployesMetLEffectifAZero() {
        Agence existante = agenceValide();
        existante.setIdAgence(1L);
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(existante));
        when(agenceRepository.save(any(Agence.class))).thenAnswer(returnsFirstArg());

        Agence resultat = service.ajouterEmployes(1L, agenceValide());

        assertEquals(0, resultat.getEffectif());
        verify(employeService, never()).create(any());
    }

    @Test
    void ajouterEmployesPropageLErreurSiUnEmployeEstInvalide() {
        Agence existante = agenceValide();
        existante.setIdAgence(1L);
        Employe valide = employe("Ben Salah", "Ali");
        Employe invalide = employe("", "Sarra");
        Agence demande = agenceValide();
        demande.setEmployes(new ArrayList<>(List.of(valide, invalide)));
        when(agenceRepository.findById(1L)).thenReturn(Optional.of(existante));
        when(agenceRepository.save(any(Agence.class))).thenAnswer(returnsFirstArg());
        when(employeService.create(valide)).thenReturn(valide);
        when(employeService.create(invalide)).thenThrow(new IllegalArgumentException("Données invalides"));

        assertThrows(IllegalArgumentException.class, () -> service.ajouterEmployes(1L, demande));
    }
}
