package upc.edu.pe.parkeoya.backend.v1.reservations.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.commands.UpdateReservationStatusCommand;

public class UpdateReservationCommandFromResourceAssembler {
    public static UpdateReservationStatusCommand toCommandFromResource(Long reservationId, String status) {
        return new UpdateReservationStatusCommand(reservationId, status);
    }
}
