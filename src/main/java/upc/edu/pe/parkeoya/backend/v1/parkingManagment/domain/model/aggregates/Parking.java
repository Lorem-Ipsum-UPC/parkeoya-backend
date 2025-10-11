package upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.aggregates;

import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands.AddParkingSpotCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.commands.CreateParkingCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.entities.ParkingSpot;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.valueobjects.OwnerId;
import upc.edu.pe.parkeoya.backend.v1.parkingManagment.domain.model.valueobjects.SpotManager;
import upc.edu.pe.parkeoya.backend.v1.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
public class Parking extends AuditableAbstractAggregateRoot<Parking> {

    @Embedded
    private OwnerId ownerId;

    @Getter
    @NotNull
    private String name;

    @Getter
    @NotNull
    private String description;

    @Getter
    @NotNull
    private String address;

    @Getter
    @Setter
    @NotNull
    private Float ratePerHour;

    @Getter
    @Setter
    private Float rating;

    @Getter
    @Setter
    @NotNull
    private Integer totalSpots;

    @Getter
    @Setter
    @NotNull
    private Integer availableSpots;

    @Getter
    @Setter
    @NotNull
    private Integer totalRows;

    @Getter
    @Setter
    @NotNull
    private Integer totalColumns;

    @Getter
    @Setter
    @NotNull
    private String imageUrl;

    @Embedded
    private SpotManager parkingSpotManager;

    protected Parking() {}

    public Parking(CreateParkingCommand command) {
        this.ownerId = new OwnerId(command.ownerId());
        this.name = command.name();
        this.description = command.description();
        this.address = command.address();
        this.ratePerHour = command.ratePerHour();
        this.rating = 0f;
        this.totalSpots = command.totalSpots();
        this.availableSpots = command.availableSpots();
        this.totalRows = command.totalRows();
        this.totalColumns = command.totalColumns();
        this.imageUrl = command.imageUrl();
        this.parkingSpotManager = new SpotManager();
    }

    public void addParkingSpot(AddParkingSpotCommand command) {
        parkingSpotManager.addParkingSpot(this, command.row(), command.column(), command.label());
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpotManager.getParkingSpots();
    }

    public ParkingSpot getParkingSpot(UUID parkingSpotId) {
        return parkingSpotManager.getParkingSpotById(parkingSpotId);
    }

    public Long getOwnerId() {
        return this.ownerId.ownerId();
    }

}
