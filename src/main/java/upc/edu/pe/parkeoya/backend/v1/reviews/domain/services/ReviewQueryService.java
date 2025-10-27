package upc.edu.pe.parkeoya.backend.v1.reviews.domain.services;

import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.aggregates.Review;
import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.queries.GetReviewsByDriverIdQuery;
import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.queries.GetReviewsByParkingIdQuery;

import java.util.List;

public interface ReviewQueryService {
    List<Review> handle(GetReviewsByDriverIdQuery query);
    List<Review> handle(GetReviewsByParkingIdQuery query);
}
