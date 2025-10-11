package upc.edu.pe.parkeoya.backend.v1.parkingManagment.application.internal.commandservices;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands.AddParkingSpotCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands.CreateParkingCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.entities.ParkingSpot;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.services.ParkingCommandService;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.infrastructure.persistence.jpa.repositories.ParkingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingCommandServiceImpl implements ParkingCommandService {

    private final ParkingRepository parkingRepository;

    public ParkingCommandServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    public Optional<Parking> handle(CreateParkingCommand command) {
        var parking = new Parking(command);
        var createdParking = parkingRepository.save(parking);
        return Optional.of(createdParking);
    }

    @Override
    public Optional<ParkingSpot> handle(AddParkingSpotCommand command) {
        var parking = this.parkingRepository.findById(command.parkingId())
                .orElseThrow(() -> new IllegalArgumentException("Parking not found"));

        parking.addParkingSpot(command);
        var updatedParking = parkingRepository.save(parking);
        return updatedParking.getParkingSpots().stream()
                .filter(parkingSpot -> parkingSpot.getParkingId().equals(command.parkingId()))
                .findFirst();
    }
}
