package upc.edu.pe.parkeoya.backend.v1.profile.application.internal.commandservices;

import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.Driver;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateDriverCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.DriverCommandService;
import upc.edu.pe.parkeoya.backend.v1.profile.infrastructure.persistence.jpa.repositories.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverCommandServiceImpl implements DriverCommandService {
    private final DriverRepository driverRepository;

    public DriverCommandServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Optional<Driver> handle(CreateDriverCommand command, Long userId) {

        if (driverRepository.existsByPhone_Phone(command.phone())) {
            throw new IllegalArgumentException("Phone already exists");
        }

        if (driverRepository.existsByDni_Dni(command.dni())) {
            throw new IllegalArgumentException("DNI already exists");
        }
        var driver = new Driver(command, userId);
        var createdDriver = driverRepository.save(driver);
        return Optional.of(createdDriver);
    }
}
