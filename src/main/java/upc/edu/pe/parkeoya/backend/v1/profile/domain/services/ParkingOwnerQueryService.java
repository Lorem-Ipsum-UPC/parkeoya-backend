package upc.edu.pe.parkeoya.backend.v1.profile.domain.services;





import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.aggregates.ParkingOwner;
import upc.edu.pe.parkeoya.backend.v1.profile.domain.model.queries.GetParkingOwnerByUserIdAsyncQuery;

import java.util.Optional;

public interface ParkingOwnerQueryService {
    Optional<ParkingOwner> handle(GetParkingOwnerByUserIdAsyncQuery query);
}
