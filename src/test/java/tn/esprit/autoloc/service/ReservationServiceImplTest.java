package tn.esprit.autoloc.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.autoloc.domain.Reservation;
import tn.esprit.autoloc.repository.IReservationRepository;

import java.time.LocalDate;

class ReservationServiceImplTest extends AbstractGenericServiceTest<Reservation, Long> {

    @Mock
    private IReservationRepository repository;

    @InjectMocks
    private ReservationServiceImpl service;

    @Override
    protected IGenericService<Reservation, Long> service() {
        return service;
    }

    @Override
    protected JpaRepository<Reservation, Long> repository() {
        return repository;
    }

    @Override
    protected Reservation entiteValide() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 11, 1));
        reservation.setDateFin(LocalDate.of(2026, 11, 5));
        return reservation;
    }

    @Override
    protected Reservation entiteInvalide() {
        Reservation reservation = new Reservation();
        reservation.setDateDebut(LocalDate.of(2026, 11, 5));
        reservation.setDateFin(LocalDate.of(2026, 11, 1));   // fin avant le début
        return reservation;
    }

    @Override
    protected Reservation entiteAvecId() {
        Reservation reservation = entiteValide();
        reservation.setIdReservation(1L);
        return reservation;
    }

    @Override
    protected Long id() {
        return 1L;
    }
}
