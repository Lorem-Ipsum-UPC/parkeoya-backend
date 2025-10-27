package upc.edu.pe.parkeoya.backend.v1.reviews.domain.services;

import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.aggregates.Review;
import upc.edu.pe.parkeoya.backend.v1.reviews.domain.model.commands.CreateReviewCommand;

import java.util.Optional;

public interface ReviewCommandService {
    Optional<Review> handle(CreateReviewCommand command);
}
