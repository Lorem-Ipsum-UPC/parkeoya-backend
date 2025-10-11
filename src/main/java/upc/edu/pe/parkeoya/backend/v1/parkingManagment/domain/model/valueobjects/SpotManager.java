package upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.valueobjects;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.entities.ParkingSpot;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Embeddable
@Getter
public class SpotManager {

    @OneToMany(mappedBy = "parking", cascade = CascadeType.ALL)
    private List<ParkingSpot> parkingSpots;

    public SpotManager() {
        this.parkingSpots = new ArrayList<>();
    }

    public void addParkingSpot(Parking parking, Integer row, Integer column, String label) {
        ParkingSpot parkingSpot = new ParkingSpot(parking, row, column, label);
        this.parkingSpots.add(parkingSpot);
    }

    public void removeParkingSpot(UUID parkingSpotId) {
        this.parkingSpots.removeIf(parkingSpot -> parkingSpot.getId().equals(parkingSpotId));
    }

    public ParkingSpot getParkingSpotById(UUID parkingSpotId) {
        return this.parkingSpots.stream()
                .filter(parkingSpot -> parkingSpot.getId().equals(parkingSpotId))
                .findFirst()
                .orElse(null);
    }
}
