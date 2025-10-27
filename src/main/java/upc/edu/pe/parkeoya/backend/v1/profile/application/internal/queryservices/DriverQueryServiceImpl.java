package upc.edu.pe.parkeoya.backend.v1.profile.application.internal.queryservices;


import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.Driver;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetDriverByUserIdAsyncQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetDriverFullNameByUserIdQuery;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.services.DriverQueryService;
import upc.edu.pe.parkeoya.backend.v1.profile.infrastructure.persistence.jpa.repositories.DriverRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DriverQueryServiceImpl implements DriverQueryService {
    private final DriverRepository driverRepository;

    public DriverQueryServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Optional<Driver> handle(GetDriverByUserIdAsyncQuery query) {
        return driverRepository.findDriverByUserId(query.userId());
    }

    @Override
    public Optional<String> handle(GetDriverFullNameByUserIdQuery query) {
        var driver = driverRepository.findDriverByUserId(query.userId());
        return driver.map(Driver::getFullName);
    }
}
