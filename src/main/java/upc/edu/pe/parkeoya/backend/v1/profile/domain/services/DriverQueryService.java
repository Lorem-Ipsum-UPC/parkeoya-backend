package upc.edu.pe.parkeoya.backend.v1.profile.domain.services;





import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.Driver;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetDriverByUserIdAsyncQuery;

import java.util.Optional;

public interface DriverQueryService {
    Optional<Driver> handle(GetDriverByUserIdAsyncQuery query);
}
