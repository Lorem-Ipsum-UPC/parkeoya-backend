package upc.edu.pe.parkeoya.backend.v1.parkingManagment.interfaces.rest.resources;

public record AddParkingSpotResource(
        Integer row,
        Integer column,
        String label
) {
}
