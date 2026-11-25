package tn.esprit.autoloc.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
abstract class AbstractGenericServiceTest<T, K> {

    protected abstract IGenericService<T, K> service();

    protected abstract JpaRepository<T, K> repository();

    /** Entité valide, sans identifiant. */
    protected abstract T entiteValide();

    /** Entité invalide, sans identifiant. */
    protected abstract T entiteInvalide();

    /** Entité valide, avec identifiant. */
    protected abstract T entiteAvecId();

    protected abstract K id();

    @Test
    void createEnregistreUneEntiteValide() {
        T entite = entiteValide();
        when(repository().save(any())).thenAnswer(returnsFirstArg());

        T resultat = service().create(entite);

        assertSame(entite, resultat);
        verify(repository()).save(entite);
    }

    @Test
    void createRefuseUneEntiteAvecIdentifiant() {
        T entite = entiteAvecId();
        IGenericService<T, K> serviceTeste = service();

        assertThrows(IllegalArgumentException.class, () -> serviceTeste.create(entite));
        verify(repository(), never()).save(any());
    }

    @Test
    void createRefuseUneEntiteInvalide() {
        T entite = entiteInvalide();
        IGenericService<T, K> serviceTeste = service();

        assertThrows(IllegalArgumentException.class, () -> serviceTeste.create(entite));
        verify(repository(), never()).save(any());
    }

    @Test
    void findByIdRetourneLEntiteExistante() {
        T entite = entiteAvecId();
        when(repository().findById(id())).thenReturn(Optional.of(entite));

        assertSame(entite, service().findById(id()));
    }

    @Test
    void findByIdLeveUneExceptionSiInexistante() {
        when(repository().findById(id())).thenReturn(Optional.empty());

        IGenericService<T, K> serviceTeste = service();
        K identifiant = id();
        assertThrows(ResourceNotFoundException.class, () -> serviceTeste.findById(identifiant));
    }

    @Test
    void findAllRetourneToutesLesEntites() {
        when(repository().findAll()).thenReturn(List.of(entiteAvecId()));

        assertEquals(1, service().findAll().size());
    }

    @Test
    void updateEnregistreUneEntiteValide() {
        when(repository().findById(id())).thenReturn(Optional.of(entiteAvecId()));
        when(repository().save(any())).thenAnswer(returnsFirstArg());

        T resultat = service().update(id(), entiteValide());

        assertNotNull(resultat);
        verify(repository()).save(any());
    }

    @Test
    void updateRefuseUneEntiteInvalide() {
        when(repository().findById(id())).thenReturn(Optional.of(entiteAvecId()));

        IGenericService<T, K> serviceTeste = service();
        K identifiant = id();
        T invalide = entiteInvalide();
        assertThrows(IllegalArgumentException.class, () -> serviceTeste.update(identifiant, invalide));
        verify(repository(), never()).save(any());
    }

    @Test
    void updateLeveUneExceptionSiInexistante() {
        when(repository().findById(id())).thenReturn(Optional.empty());

        IGenericService<T, K> serviceTeste = service();
        K identifiant = id();
        T valide = entiteValide();
        assertThrows(ResourceNotFoundException.class, () -> serviceTeste.update(identifiant, valide));
    }

    @Test
    void deleteByIdSupprimeUneEntiteExistante() {
        when(repository().existsById(id())).thenReturn(true);

        IGenericService<T, K> serviceTeste = service();
        K identifiant = id();
        assertDoesNotThrow(() -> serviceTeste.deleteById(identifiant));
        verify(repository()).deleteById(identifiant);
    }

    @Test
    void deleteByIdLeveUneExceptionSiInexistante() {
        when(repository().existsById(id())).thenReturn(false);

        IGenericService<T, K> serviceTeste = service();
        K identifiant = id();
        assertThrows(ResourceNotFoundException.class, () -> serviceTeste.deleteById(identifiant));
        verify(repository(), never()).deleteById(any());
    }

    @Test
    void checkRefuseNull() {
        assertFalse(service().check(null, true));
    }

    @Test
    void checkAccepteUneEntiteValide() {
        assertTrue(service().check(entiteValide(), true));
    }

    @Test
    void checkRefuseUneEntiteInvalide() {
        assertFalse(service().check(entiteInvalide(), true));
    }
}