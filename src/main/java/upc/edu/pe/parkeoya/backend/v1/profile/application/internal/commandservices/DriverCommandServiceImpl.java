package upc.edu.pe.parkeoya.backend.v1.profile.application.internal.commandservices;

import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.Driver;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateDriverCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.UpdateDriverCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.valueobjects.Dni;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.valueobjects.Phone;
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

    @Override
    public Optional<Driver> handle(UpdateDriverCommand command) {
        var driver = driverRepository.findById(command.driverId())
                .orElseThrow(() -> new IllegalArgumentException("Driver not found"));

        if (command.phone() != null && !command.phone().equals(driver.getPhone())) {
            if (driverRepository.existsByPhone_Phone(command.phone())) {
                throw new IllegalArgumentException("Phone already exists");
            }
        }

        if (command.dni() != null && !command.dni().equals(driver.getDni())) {
            if (driverRepository.existsByDni_Dni(command.dni())) {
                throw new IllegalArgumentException("DNI already exists");
            }
        }

        if (command.fullName() != null) driver.setFullName(command.fullName());
        if (command.city() != null) driver.setCity(command.city());
        if (command.country() != null) driver.setCountry(command.country());
        if (command.phone() != null) driver.setPhone(new Phone(command.phone()));
        if (command.dni() != null) driver.setDni(new Dni(command.dni()));

        var updated = driverRepository.save(driver);
        return Optional.of(updated);
    }
}
