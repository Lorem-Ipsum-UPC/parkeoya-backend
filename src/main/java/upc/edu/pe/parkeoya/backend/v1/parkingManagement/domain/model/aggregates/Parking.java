package upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.aggregates;

import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands.AddParkingSpotCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.commands.CreateParkingCommand;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.entities.ParkingSpot;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.valueobjects.OwnerId;
import upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.valueobjects.SpotManager;
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
    @NotNull
    private Double lat;

    @Getter
    @NotNull
    private Double lng;

    @Getter
    @Setter
    @NotNull
    private Float ratePerHour;

    @Getter
    @Setter
    private Float rating;

    @Getter
    @Setter
    private Float ratingCount;

    @Getter
    private Float averageRating;

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
        this.lat = command.lat();
        this.lng = command.lng();
        this.ratePerHour = command.ratePerHour();
        this.rating = 0f;
        this.ratingCount = 0f;
        this.averageRating = 0f;
        this.totalSpots = command.totalSpots();
        this.availableSpots = command.availableSpots();
        this.totalRows = command.totalRows();
        this.totalColumns = command.totalColumns();
        this.imageUrl = command.imageUrl();
        this.parkingSpotManager = new SpotManager();
    }

    public void setRating(Float rating) {
        this.rating += rating;
        this.ratingCount += 1;
        this.averageRating = this.rating / this.ratingCount;
    }

    public ParkingSpot addParkingSpot(AddParkingSpotCommand command) {
       return parkingSpotManager.addParkingSpot(this, command.row(), command.column(), command.label());
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpotManager.getParkingSpots();
    }

    public ParkingSpot getParkingSpot(UUID parkingSpotId) {
        return parkingSpotManager.getParkingSpotById(parkingSpotId);
    }

    public void updateAvailableSpotsCount(Integer numberAvailable, String operation) {
        if (operation.equals("add")) {
            this.availableSpots += numberAvailable;
        } else if (operation.equals("subtract")) {
            this.availableSpots -= numberAvailable;
        } else {
            throw new IllegalArgumentException("Invalid operation");
        }
    }

    public Long getOwnerId() {
        return this.ownerId.ownerId();
    }

}
