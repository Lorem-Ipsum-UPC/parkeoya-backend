package upc.edu.pe.parkeoya.backend.v1.deviceManagement.application.internal.queryservices;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.aggregates.EdgeServer;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.queries.GetEdgeServerByParkingIdQuery;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.services.EdgeServerQueryService;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.infrastructure.persistence.jpa.repositories.EdgeServerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EdgeServerQueryServiceImpl implements EdgeServerQueryService {

    private final EdgeServerRepository edgeServerRepository;

    public EdgeServerQueryServiceImpl(EdgeServerRepository edgeServerRepository) {
        this.edgeServerRepository = edgeServerRepository;
    }

    @Override
    public List<EdgeServer> handle(GetEdgeServerByParkingIdQuery query) {
        return edgeServerRepository.findByParkingIdParkingId(query.parkingId());
    }
}
