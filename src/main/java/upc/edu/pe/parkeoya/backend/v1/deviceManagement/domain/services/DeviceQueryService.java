package upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.services;

import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.aggregates.Device;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.queries.GetDeviceByParkingSpotIdQuery;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.queries.GetDevicesByEdgeServerIdQuery;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.queries.GetDevicesByParkingIdQuery;
import upc.edu.pe.parkeoya.backend.v1.deviceManagement.domain.model.queries.GetUnassignedDevicesByParkingIdQuery;

import java.util.List;
import java.util.Optional;

public interface DeviceQueryService {
    List<Device> handle(GetDevicesByParkingIdQuery query);
    Optional<Device> handle(GetDeviceByParkingSpotIdQuery query);
    List<Device> handle(GetUnassignedDevicesByParkingIdQuery query);
    List<Device> handle(GetDevicesByEdgeServerIdQuery query);
}
