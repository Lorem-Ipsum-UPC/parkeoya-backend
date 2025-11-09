package upc.edu.pe.parkeoya.backend.v1.profile.application.internal.commandservices;


import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.ParkingOwner;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.CreateParkingOwnerCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.commands.UpdateParkingOwnerCommand;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.valueobjects.Phone;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.valueobjects.Ruc;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.ParkingOwnerCommandService;
import upc.edu.pe.parkeoya.backend.v1.profile.infrastructure.persistence.jpa.repositories.ProductOwnerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ParkingOwnerCommandServiceImpl implements ParkingOwnerCommandService {
    private final ProductOwnerRepository distributorRepository;

    public ParkingOwnerCommandServiceImpl(ProductOwnerRepository productOwnerRepository) {
        this.distributorRepository = productOwnerRepository;
    }

    @Override
    public Optional<ParkingOwner> handle(CreateParkingOwnerCommand command, Long userId) {
        if (distributorRepository.existsByPhone_Phone(command.phone())){
            throw new IllegalArgumentException("Phone already exists");
        }

        if (distributorRepository.existsByRuc_Ruc(command.ruc())){
            throw new IllegalArgumentException("Ruc already exists");
        }

        var parkingOwner = new ParkingOwner(command, userId);
        var createdParkingOwner = distributorRepository.save(parkingOwner);
        return Optional.of(createdParkingOwner);
    }

    @Override
    public Optional<ParkingOwner> handle(UpdateParkingOwnerCommand command) {
        var parkingOwner = distributorRepository.findById(command.parkingOwnerId())
                .orElseThrow(() -> new IllegalArgumentException("Parking owner not found"));

        if (command.phone() != null && !command.phone().equals(parkingOwner.getPhone())) {
            if (distributorRepository.existsByPhone_Phone(command.phone())) {
                throw new IllegalArgumentException("Phone already exists");
            }
        }

        if (command.ruc() != null && !command.ruc().equals(parkingOwner.getRuc())) {
            if (distributorRepository.existsByRuc_Ruc(command.ruc())) {
                throw new IllegalArgumentException("RUC already exists");
            }
        }

        if (command.fullName() != null) parkingOwner.setFullName(command.fullName());
        if (command.city() != null) parkingOwner.setCity(command.city());
        if (command.country() != null) parkingOwner.setCountry(command.country());
        if (command.phone() != null) parkingOwner.setPhone(new Phone(command.phone()));
        if (command.companyName() != null) parkingOwner.setCompanyName(command.companyName());
        if (command.ruc() != null) parkingOwner.setRuc(new Ruc(command.ruc()));

        var updated = distributorRepository.save(parkingOwner);
        return Optional.of(updated);
    }
}
