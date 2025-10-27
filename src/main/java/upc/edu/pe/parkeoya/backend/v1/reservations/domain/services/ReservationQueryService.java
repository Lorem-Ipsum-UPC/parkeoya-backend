package upc.edu.pe.parkeoya.backend.v1.reservations.domain.services;

import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.aggregates.Reservation;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.queries.GetAllReservationsByDriverIdAndStatusQuery;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.queries.GetAllReservationsByDriverIdQuery;
import upc.edu.pe.parkeoya.backend.v1.reservations.domain.model.queries.GetAllReservationsByParkingIdQuery;

import java.util.List;

public interface ReservationQueryService {
    List<Reservation> handle(GetAllReservationsByParkingIdQuery query);
    List<Reservation> handle(GetAllReservationsByDriverIdQuery query);
    List<Reservation> handle(GetAllReservationsByDriverIdAndStatusQuery query);
}
