package upc.edu.pe.parkeoya.backend.v1.reservations.domain.services;

import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.aggregates.Reservation;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.commands.CreateReservationCommand;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.commands.UpdateReservationStatusCommand;

import java.io.IOException;
import java.util.Optional;

public interface ReservationCommandService {
    Optional<Reservation> handle(CreateReservationCommand command) throws IOException;
    Optional<Reservation> handle(UpdateReservationStatusCommand command) throws IOException;
}
