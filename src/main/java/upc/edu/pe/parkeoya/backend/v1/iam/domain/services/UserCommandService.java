package upc.edu.pe.parkeoya.backend.v1.iam.domain.services;

import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.aggregates.User;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.commands.SignInCommand;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.commands.SignUpDriverCommand;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.commands.SignUpParkingOwnerCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;

public interface UserCommandService {
    Optional<ImmutablePair<User, String>> handle(SignInCommand command);
    //Optional<User> handle(SignUpCommand command);
    Optional<User> handle(SignUpDriverCommand command);
    Optional<User> handle(SignUpParkingOwnerCommand command);
}
