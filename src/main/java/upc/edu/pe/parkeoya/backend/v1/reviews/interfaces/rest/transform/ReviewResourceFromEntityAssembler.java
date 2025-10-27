package upc.edu.pe.parkeoya.backend.v1.reviews.interfaces.rest.transform;

import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.aggregates.Review;
import upc.edu.pe.parkeoya.backend.v1.reviews.interfaces.rest.resources.ReviewResource;

public class ReviewResourceFromEntityAssembler {
    public static ReviewResource toResourceFromEntity(Review entity) {
        return new ReviewResource(
                entity.getId(),
                entity.getDriverId(),
                entity.getDriverName(),
                entity.getParkingId(),
                entity.getParkingName(),
                entity.getComment(),
                entity.getRating(),
                entity.getCreatedAt().toString()
        );
    }
}
