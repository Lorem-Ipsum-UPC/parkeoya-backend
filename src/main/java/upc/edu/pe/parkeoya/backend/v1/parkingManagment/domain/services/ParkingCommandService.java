package upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.services;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands.AddParkingSpotCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands.CreateParkingCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.entities.ParkingSpot;

import java.util.Optional;

public interface ParkingCommandService {

    Optional<Parking> handle(CreateParkingCommand command);

    Optional<ParkingSpot> handle(AddParkingSpotCommand command);
}
