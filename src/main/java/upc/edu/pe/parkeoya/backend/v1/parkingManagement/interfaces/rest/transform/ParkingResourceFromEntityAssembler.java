package upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.interfaces.rest.resources.ParkingResource;

public class ParkingResourceFromEntityAssembler {
    public static ParkingResource toResourceFromEntity(Parking entity) {
        return new ParkingResource(
                entity.getId(),
                entity.getOwnerId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getCity(),
                entity.getProvince(),
                entity.getPostalCode(),
                entity.getLat(),
                entity.getLng(),
                entity.getRatePerHour(),
                entity.getDailyRate(),
                entity.getMonthlyRate(),
                entity.getAverageRating(),
                entity.getRatingCount(),
                entity.getTotalSpots(),
                entity.getRegularSpots(),
                entity.getDisabledSpots(),
                entity.getElectricSpots(),
                entity.getAvailableSpots(),
                entity.getTotalRows(),
                entity.getTotalColumns(),
                entity.getImageUrl(),
                entity.getOperatingDays(),
                entity.getOpen24Hours(),
                entity.getOpeningTime(),
                entity.getClosingTime()
        );
    }
}
