package upc.edu.pe.parkeoya.backend.v1.iam.domain.services;

import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.aggregates.User;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.queries.CheckUserByIdQuery;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.queries.GetAllUsersQuery;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.queries.GetUserByIdQuery;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.queries.GetUserByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery query);
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByUsernameQuery query);
    boolean handle(CheckUserByIdQuery query);
}
