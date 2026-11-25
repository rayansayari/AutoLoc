package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.autoloc.domain.Paiement;
import tn.esprit.autoloc.exception.ResourceNotFoundException;
import tn.esprit.autoloc.repository.IPaiementRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaiementServiceImplTest {

    @Mock
    private IPaiementRepository paiementRepository;

    @InjectMocks
    private PaiementServiceImpl service;

    private Paiement paiement() {
        Paiement paiement = new Paiement();
        paiement.setIdPaiement(1L);
        paiement.setMontant(new BigDecimal("120.00"));
        return paiement;
    }

    @Test
    void findByIdRetourneLePaiementExistant() {
        Paiement paiement = paiement();
        when(paiementRepository.findById(1L)).thenReturn(Optional.of(paiement));

        assertSame(paiement, service.findById(1L));
    }

    @Test
    void findByIdLeveUneExceptionSiInexistant() {
        when(paiementRepository.findById(9L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(9L));
    }

    @Test
    void findAllRetourneTousLesPaiements() {
        when(paiementRepository.findAll()).thenReturn(List.of(paiement(), paiement()));

        assertEquals(2, service.findAll().size());
    }
}
