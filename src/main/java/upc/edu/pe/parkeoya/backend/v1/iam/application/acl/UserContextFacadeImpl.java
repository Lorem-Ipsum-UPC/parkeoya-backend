package upc.edu.pe.parkeoya.backend.v1.iam.application.acl;


import upc.edu.pe.parkeoya.backend.v1.iam.domain.model.queries.GetUserByIdQuery;
import upc.edu.pe.parkeoya.backend.v1.iam.domain.services.UserQueryService;
import upc.edu.pe.parkeoya.backend.v1.iam.interfaces.acl.UsersContextFacade;
import org.springframework.stereotype.Service;

@Service
public class UserContextFacadeImpl implements UsersContextFacade {
    private final UserQueryService userQueryService;

    public UserContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    // inherited javadoc
    @Override
    public boolean exitsUserById(Long id) {
        var query = new GetUserByIdQuery(id);
        var existingUser = userQueryService.handle(query);
        return existingUser.isPresent();
    }
}
