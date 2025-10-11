package upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.entities;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.aggregates.Parking;
import upc.edu.pe.parkeoya.backend.v1.shared.domain.model.entities.AuditableModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Entity
public class ParkingSpot extends AuditableModel {

    @Id
    private final UUID id;

    @ManyToOne
    @JoinColumn(name = "parking_id", nullable = false)
    @NotNull
    private Parking parking;

    @Setter
    private Boolean available;

    @Setter
    private Integer rowIndex;

    @Setter
    private Integer columnIndex;

    @Setter
    private String label;

    protected ParkingSpot() {
        this.id = null;
    }

    public ParkingSpot(Parking parking, Integer row, Integer column, String label) {
        this.id = UUID.randomUUID();
        this.parking = parking;
        this.available = true;
        this.rowIndex = row;
        this.columnIndex = column;
        this.label = label;
    }

    public Long getParkingId() {
        return this.parking.getId();
    }
}
