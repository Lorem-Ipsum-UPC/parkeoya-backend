package upc.edu.pe.parkeoya.backend.v1.profile.domain.services;





import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.Driver;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateDriverCommand;

import java.util.Optional;

public interface DriverCommandService {
    Optional<Driver> handle(CreateDriverCommand command, Long userId);
}
