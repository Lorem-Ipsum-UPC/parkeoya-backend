package upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.services;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.aggregates.EdgeServer;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.queries.GetEdgeServerByParkingIdQuery;

import java.util.List;

public interface EdgeServerQueryService {
    List<EdgeServer> handle(GetEdgeServerByParkingIdQuery query);
}
