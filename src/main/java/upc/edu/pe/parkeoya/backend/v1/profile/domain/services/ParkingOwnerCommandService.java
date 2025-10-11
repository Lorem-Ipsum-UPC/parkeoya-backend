package upc.edu.pe.parkeoya.backend.v1.profile.domain.services;





import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.ParkingOwner;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateParkingOwnerCommand;

import java.util.Optional;

public interface ParkingOwnerCommandService {
    Optional<ParkingOwner> handle(CreateParkingOwnerCommand command, Long userId);
}
