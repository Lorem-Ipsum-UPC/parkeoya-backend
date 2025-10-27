package upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.commands;

public record UpdateReservationStatusCommand(
        Long reservationId,
        String status
) {
}
