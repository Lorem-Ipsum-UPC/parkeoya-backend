package upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.aggregates;

import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.commands.CreateReviewCommand;
import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.valueobjects.DriverId;
import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.valueobjects.ParkingId;
import upc.edu.pe.parkeoya.backend.v1.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
public class Review extends AuditableAbstractAggregateRoot<Review> {

    @Embedded
    private DriverId driverId;

    @Getter
    private String driverName;

    @Embedded
    private ParkingId parkingId;

    @Getter
    private String parkingName;

    @Getter
    private String comment;

    @Getter
    private Float rating;

    protected Review() {}

    public Review(CreateReviewCommand command, String driverName, String parkingName) {
        this.driverId = new DriverId(command.driverId());
        this.driverName = driverName;
        this.parkingId = new ParkingId(command.parkingId());
        this.parkingName = parkingName;
        this.comment = command.comment();
        this.rating = command.rating();
    }

    public Long getDriverId() {
        return driverId.driverId();
    }

    public Long getParkingId() {
        return parkingId.parkingId();
    }
}
