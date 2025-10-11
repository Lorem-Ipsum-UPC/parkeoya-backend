package upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.services;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.entities.ParkingSpot;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.queries.GetParkingByIdQuery;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.queries.GetParkingSpotByIdQuery;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.queries.GetParkingSpotsByParkingIdQuery;

import java.util.List;
import java.util.Optional;

public interface ParkingQueryService {

    Optional<Parking> handle(GetParkingByIdQuery query);

    List<ParkingSpot> handle(GetParkingSpotsByParkingIdQuery query);
    Optional<ParkingSpot> handle(GetParkingSpotByIdQuery query);
}