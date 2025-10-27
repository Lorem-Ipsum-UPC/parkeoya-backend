package upc.edu.pe.parkeoya.backend.v1.parkingManagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record OwnerId(Long ownerId) {
    public OwnerId {
        if (ownerId == null || ownerId <= 0) {
            throw new IllegalArgumentException("Owner ID must be a positive number");
        }
    }
    public OwnerId() { this(0L); } // Default constructor for serialization/deserialization
}